package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.model.Location

val location = Location(
    name = "",
    region = "",
    latitude = 0.0,
    longitude = 0.0
)
val launchpadDetail = LaunchpadDetailData(
    status = "",
    location = location,
    site_name_long = ""
)

val launchpadDataState = LaunchpadViewState.Data(
    fullName = launchpadDetail.site_name_long,
    status = launchpadDetail.status,
    lat = location.latitude.toString(),
    lng = location.longitude.toString()
)