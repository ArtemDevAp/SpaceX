package com.artem.mi.spacexautenticom.repository

import com.artem.mi.spacexautenticom.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.cache.LaunchpadDetailCache
import com.artem.mi.spacexautenticom.ext.parseErrorBody
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.network.ISpaceXLaunchpadClient
import com.artem.mi.spacexautenticom.preload.IPreload
import com.squareup.moshi.Moshi
import javax.inject.Inject


class LaunchpadRepo @Inject constructor(
    private val launchpadCache: LaunchpadCache,
    private val launchpadDetailCache: LaunchpadDetailCache,
    private val iSpaceXClient: ISpaceXLaunchpadClient,
    private val moshi: Moshi
) : IPreload {

    suspend fun fetchLaunchpads(): List<LaunchpadData> {
        return if (!launchpadCache.isExpired) {
            launchpadCache.getAll()
        } else {
            iSpaceXClient.fetchLaunchpads().apply {
                launchpadCache.addAll(this)
            }
        }
    }

    suspend fun fetchDetailLaunchpad(siteId: String):
            ApiResponse<LaunchpadDetailData, ErrorResponse> {

        return try {

            if (launchpadDetailCache.isExpired) launchpadDetailCache.clear()

            if (launchpadDetailCache.hasKey(siteId)) {
                return ApiResponse.Success(launchpadDetailCache.get(siteId)!!)
            }

            val result = iSpaceXClient.fetchDetailLaunchpad(siteId)
            if (result.isSuccessful) result.body()?.let { response ->
                launchpadDetailCache.add(siteId, response)
                ApiResponse.Success(response)
            } else {
                result.errorBody()?.let { responseError ->
                    moshi.parseErrorBody<ErrorResponse>(responseError)?.let { errorResponse ->
                        ApiResponse.Error(errorResponse)
                    }
                }
            } ?: ApiResponse.Error(ErrorResponse("error to fetch response"))
        } catch (t: Throwable) {
            ApiResponse.Error(ErrorResponse(t.localizedMessage ?: "error read error"))
        }

    }

    override suspend fun run() {
        fetchLaunchpads()
    }
}
