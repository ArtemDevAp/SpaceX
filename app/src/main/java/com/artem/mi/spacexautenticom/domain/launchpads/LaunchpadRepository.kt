package com.artem.mi.spacexautenticom.domain.launchpads

import com.artem.mi.spacexautenticom.data.network.ErrorResponse
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.artem.mi.spacexautenticom.data.network.LaunchpadDetailData
import com.artem.mi.spacexautenticom.domain.core.SPXResult

interface LaunchpadRepository {
    suspend fun fetchLaunchpads(): List<LaunchpadData>
    suspend fun fetchDetailLaunchpad(siteId: String): SPXResult<LaunchpadDetailData, ErrorResponse>
}