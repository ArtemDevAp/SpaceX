package com.artem.mi.spacexautenticom.repository

import com.artem.mi.spacexautenticom.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.cache.LaunchpadDetailCache
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.network.ISpaceXLaunchpadClient
import com.artem.mi.spacexautenticom.preload.IPreload
import java.util.concurrent.CancellationException
import javax.inject.Inject

interface LaunchpadRepository {
    suspend fun fetchLaunchpads(): List<LaunchpadData>
    suspend fun fetchDetailLaunchpad(siteId: String): ApiResponse<LaunchpadDetailData, ErrorResponse>
}

class LaunchpadRepositoryImpl @Inject constructor(
    private val launchpadCache: LaunchpadCache,
    private val launchpadDetailCache: LaunchpadDetailCache,
    private val iSpaceXClient: ISpaceXLaunchpadClient
) : IPreload, LaunchpadRepository {

    override suspend fun fetchLaunchpads(): List<LaunchpadData> {
        return if (!launchpadCache.isExpired) {
            launchpadCache.getAll()
        } else {
            iSpaceXClient.fetchLaunchpads().apply {
                launchpadCache.addAll(this)
            }
        }
    }

    override suspend fun fetchDetailLaunchpad(siteId: String): ApiResponse<LaunchpadDetailData, ErrorResponse> {
        return try {

            if (launchpadDetailCache.isExpired) launchpadDetailCache.clear()

            if (launchpadDetailCache.hasKey(siteId)) {
                return ApiResponse.Success(launchpadDetailCache.get(siteId)!!)
            }

            ApiResponse.Success(iSpaceXClient.fetchDetailLaunchpad(siteId).apply {
                launchpadDetailCache.add(siteId, this)
            })
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (t: Throwable) {
            ApiResponse.Error(ErrorResponse(t.localizedMessage ?: "error read error"))
        }

    }

    override suspend fun run() {
        fetchLaunchpads()
    }
}
