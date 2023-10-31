package com.artem.mi.spacexautenticom.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchpadData(
    @SerialName("site_id") val siteId: String,
    @SerialName("site_name_long") val siteNameLong: String,
)

@Serializable
data class LaunchpadDetailData(
    val status: String,
    val location: Location,
    @SerialName("site_name_long") val siteNameLong: String
)

@Serializable
data class Location(
    val name: String,
    val region: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class ErrorResponse(val error: String)