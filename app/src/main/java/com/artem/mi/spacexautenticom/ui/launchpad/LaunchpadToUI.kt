package com.artem.mi.spacexautenticom.ui.launchpad

import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.domain.Launchpads
import javax.inject.Inject

class LaunchpadToUI @Inject constructor() {
    fun map(launchpadsDomain: Launchpads): LaunchpadUi {
        return with(launchpadsDomain) {
            if (launchpads.isEmpty()) LaunchpadUi.InfoMessage(R.string.waiting_for_network)
            else LaunchpadUi.Data(launchpads)
        }
    }
}