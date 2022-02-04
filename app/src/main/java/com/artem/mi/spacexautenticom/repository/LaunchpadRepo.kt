package com.artem.mi.spacexautenticom.repository

import android.util.Log
import com.artem.mi.spacexautenticom.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.cache.LaunchpadDetailCache
import com.artem.mi.spacexautenticom.ext.parseErrorBody
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.network.ApiClient
import com.artem.mi.spacexautenticom.preload.IPreload

object LaunchpadRepo : IPreload {

    private val retrofitApi = ApiClient.provideSpaceXClient()
    private val moshi = ApiClient.provideMoshi()
    private val launchpadCache = LaunchpadCache()

    suspend fun fetchLaunchpads(): List<LaunchpadData> {
        return if (!launchpadCache.isExpired) {
            launchpadCache.getAll()
        } else {
           retrofitApi.fetchLaunchpads().apply {
                launchpadCache.addAll(this)
            }
        }
    }

    suspend fun fetchDetailLaunchpad(siteId: String): ApiResponse<LaunchpadDetailData> {

        return try {
            LaunchpadDetailCache.getLaunchpadDetail(siteId)?.let {
                Log.v("APP", "load data preload")
                return ApiResponse.Success(it)
            }

            val result = retrofitApi.fetchDetailLaunchpad(siteId)
            Log.v("APP", "fetch data preload")
            if (result.isSuccessful) result.body()?.let { response ->
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
