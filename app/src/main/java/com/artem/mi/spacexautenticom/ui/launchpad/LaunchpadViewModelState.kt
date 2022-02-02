package com.artem.mi.spacexautenticom.ui.launchpad

import com.artem.mi.spacexautenticom.model.LaunchpadData

data class LaunchpadViewModelState(
    var error: String? = null,
    val launchpads: List<LaunchpadData> = emptyList()
)