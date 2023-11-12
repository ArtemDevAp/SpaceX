package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.artem.mi.spacexautenticom.core.Mapper
import com.artem.mi.spacexautenticom.data.network.ErrorResponse
import com.artem.mi.spacexautenticom.data.network.LaunchpadDetailData
import com.artem.mi.spacexautenticom.domain.core.SPXResult
import javax.inject.Inject

interface LaunchpadDetailUiMapper :
    Mapper<SPXResult<LaunchpadDetailData, ErrorResponse>, LaunchpadViewState>

class LaunchpadDetailUiMapperImpl @Inject constructor() : LaunchpadDetailUiMapper {

    override fun map(input: SPXResult<LaunchpadDetailData, ErrorResponse>): LaunchpadViewState {
        return when (input) {
            is SPXResult.Success -> input.data.handleSuccess()
            is SPXResult.Error -> LaunchpadViewState.Error
        }
    }

    private fun LaunchpadDetailData.handleSuccess(): LaunchpadViewState {
        return with(this) {
            LaunchpadViewState.Data(
                fullName = siteNameLong,
                status = status,
                lat = location.latitude.toString(),
                lng = location.longitude.toString()
            )
        }
    }
}