package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import javax.inject.Inject

class LaunchpadDetailUiMapper @Inject constructor() {

    fun map(response: ApiResponse<LaunchpadDetailData, ErrorResponse>): LaunchpadViewState {
        return when (response) {
            is ApiResponse.Success -> response.data.handleSuccess()
            is ApiResponse.Error -> LaunchpadViewState.Error
        }
    }

    private fun LaunchpadDetailData.handleSuccess(): LaunchpadViewState {
        return with(this) {
            LaunchpadViewState.Data(
                fullName = site_name_long,
                status = status,
                lat = location.latitude.toString(),
                lng = location.longitude.toString()
            )
        }
    }
}