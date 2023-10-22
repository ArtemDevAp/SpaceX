package com.artem.mi.spacexautenticom.model

import com.squareup.moshi.JsonClass

data class LaunchpadData(
    val site_id: String,
    val site_name_long: String,
)

data class LaunchpadDetailData(
    val status: String,
    val location: Location,
    val site_name_long: String
)

data class Location(
    val name: String,
    val region: String,
    val latitude: Double,
    val longitude: Double
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(val error: String)