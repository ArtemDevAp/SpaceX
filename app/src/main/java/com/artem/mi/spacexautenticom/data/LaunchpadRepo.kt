package com.artem.mi.spacexautenticom.data

import com.artem.mi.spacexautenticom.data.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.data.cache.LaunchpadDetailCache
import com.artem.mi.spacexautenticom.domain.core.SPXResult
import com.artem.mi.spacexautenticom.data.network.ErrorResponse
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.artem.mi.spacexautenticom.data.network.LaunchpadDetailData
import com.artem.mi.spacexautenticom.data.network.SPXLaunchpadClient
import com.artem.mi.spacexautenticom.domain.core.Preload
import com.artem.mi.spacexautenticom.domain.launchpads.LaunchpadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LaunchpadRepositoryImpl @Inject constructor(
    private val launchpadCache: LaunchpadCache,
    private val launchpadDetailCache: LaunchpadDetailCache,
    private val iSpaceXClient: SPXLaunchpadClient
) : Preload, LaunchpadRepository {

    override suspend fun fetchLaunchpads(): List<LaunchpadData> = withContext(Dispatchers.IO) {
        if (!launchpadCache.isExpired) {
            launchpadCache.getAll()
        } else {
            iSpaceXClient.fetchLaunchpads().apply {
                launchpadCache.addAll(this)
            }
        }
    }

    override suspend fun fetchDetailLaunchpad(siteId: String): SPXResult<LaunchpadDetailData, ErrorResponse> {
        return try {

            if (launchpadDetailCache.isExpired) launchpadDetailCache.clear()

            if (launchpadDetailCache.hasKey(siteId)) {
                return SPXResult.Success(launchpadDetailCache.get(siteId)!!)
            }

            SPXResult.Success(
                iSpaceXClient.fetchDetailLaunchpad(siteId).apply {
                    launchpadDetailCache.add(siteId, this)
                }
            )
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (t: Throwable) {
            SPXResult.Error(ErrorResponse(t.localizedMessage ?: "error read error"))
        }
    }

    override suspend fun run() {
        fetchLaunchpads()
    }
}