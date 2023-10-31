package com.artem.mi.spacexautenticom.network

import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import retrofit2.http.GET
import retrofit2.http.Path

interface ISpaceXLaunchpadClient {

    @GET("launchpads")
    suspend fun fetchLaunchpads(): List<LaunchpadData>

    @GET("launchpads/{site_id}")
    suspend fun fetchDetailLaunchpad(@Path("site_id") suiteId: String): LaunchpadDetailData
}