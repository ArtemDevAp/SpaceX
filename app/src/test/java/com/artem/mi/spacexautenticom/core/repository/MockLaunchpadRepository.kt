package com.artem.mi.spacexautenticom.core.repository

import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.repository.LaunchpadRepository

interface MockLaunchpadRepository : LaunchpadRepository {
    override suspend fun fetchDetailLaunchpad(
        siteId: String
    ): ApiResponse<LaunchpadDetailData, ErrorResponse> = ApiResponse.Error(ErrorResponse(""))

    override suspend fun fetchLaunchpads(): List<LaunchpadData> = emptyList()
}