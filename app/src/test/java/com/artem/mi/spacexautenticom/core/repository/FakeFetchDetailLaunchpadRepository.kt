package com.artem.mi.spacexautenticom.core.repository

import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData

class FakeFetchDetailLaunchpadRepository : MockLaunchpadRepository {

    private var detailLaunchpad: ApiResponse<LaunchpadDetailData, ErrorResponse>? = null

    override suspend fun fetchDetailLaunchpad(siteId: String): ApiResponse<LaunchpadDetailData, ErrorResponse> {
        return detailLaunchpad!!
    }

    fun setDetailLaunchpad(input: ApiResponse<LaunchpadDetailData, ErrorResponse>) {
        detailLaunchpad = input
    }
}