package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.artem.mi.spacexautenticom.core.Mapper
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import javax.inject.Inject

interface LaunchpadDetailUiMapper :
    Mapper<ApiResponse<LaunchpadDetailData, ErrorResponse>, LaunchpadViewState>

class LaunchpadDetailUiMapperImpl @Inject constructor() : LaunchpadDetailUiMapper {

    override fun map(input: ApiResponse<LaunchpadDetailData, ErrorResponse>): LaunchpadViewState {
        return when (input) {
            is ApiResponse.Success -> input.data.handleSuccess()
            is ApiResponse.Error -> LaunchpadViewState.Error
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