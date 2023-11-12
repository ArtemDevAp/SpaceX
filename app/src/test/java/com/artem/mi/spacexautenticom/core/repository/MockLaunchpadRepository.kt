package com.artem.mi.spacexautenticom.core.repository

import com.artem.mi.spacexautenticom.domain.core.SPXResult
import com.artem.mi.spacexautenticom.data.network.ErrorResponse
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.artem.mi.spacexautenticom.data.network.LaunchpadDetailData
import com.artem.mi.spacexautenticom.domain.launchpads.LaunchpadRepository

interface MockLaunchpadRepository : LaunchpadRepository {
    override suspend fun fetchDetailLaunchpad(
        siteId: String
    ): SPXResult<LaunchpadDetailData, ErrorResponse> = SPXResult.Error(ErrorResponse(""))

    override suspend fun fetchLaunchpads(): List<LaunchpadData> = emptyList()
}