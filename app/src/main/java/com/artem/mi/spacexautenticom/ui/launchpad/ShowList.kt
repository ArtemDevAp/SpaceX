package com.artem.mi.spacexautenticom.ui.launchpad

import com.artem.mi.spacexautenticom.model.LaunchpadData

interface ShowList {
    fun load(list: List<LaunchpadData>)
}