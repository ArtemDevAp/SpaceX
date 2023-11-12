package com.artem.mi.spacexautenticom.core.repository

import com.artem.mi.spacexautenticom.domain.core.SPXResult
import com.artem.mi.spacexautenticom.data.network.ErrorResponse
import com.artem.mi.spacexautenticom.data.network.LaunchpadDetailData

class FakeFetchDetailLaunchpadRepository : MockLaunchpadRepository {

    private var detailLaunchpad: SPXResult<LaunchpadDetailData, ErrorResponse>? = null

    override suspend fun fetchDetailLaunchpad(siteId: String): SPXResult<LaunchpadDetailData, ErrorResponse> {
        return detailLaunchpad!!
    }

    fun setDetailLaunchpad(input: SPXResult<LaunchpadDetailData, ErrorResponse>) {
        detailLaunchpad = input
    }
}