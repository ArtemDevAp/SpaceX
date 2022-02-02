package com.artem.mi.spacexautenticom.repository

import com.artem.mi.spacexautenticom.ext.parseErrorBody
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.network.ApiClient

class LaunchpadRepo {

    private val retrofitApi = ApiClient.provideSpaceXClient()
    private val moshi = ApiClient.provideMoshi()

    suspend fun fetchLaunchpads(): List<LaunchpadData> = retrofitApi.fetchLaunchpads()

    suspend fun fetchDetailLaunchpad(siteId: String): ApiResponse<LaunchpadDetailData> {

        return try {
            val result = retrofitApi.fetchDetailLaunchpad(siteId)

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
}
