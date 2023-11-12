package com.artem.mi.spacexautenticom.ui.launchpad

import com.artem.mi.spacexautenticom.data.network.LaunchpadData

interface ShowList {
    fun load(list: List<LaunchpadData>)
}