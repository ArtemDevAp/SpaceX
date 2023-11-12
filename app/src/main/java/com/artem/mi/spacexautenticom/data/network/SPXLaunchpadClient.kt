package com.artem.mi.spacexautenticom.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface SPXLaunchpadClient {

    @GET("launchpads")
    suspend fun fetchLaunchpads(): List<LaunchpadData>

    @GET("launchpads/{site_id}")
    suspend fun fetchDetailLaunchpad(@Path("site_id") suiteId: String): LaunchpadDetailData
}