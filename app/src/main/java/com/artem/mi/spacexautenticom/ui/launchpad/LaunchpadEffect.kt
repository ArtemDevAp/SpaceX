package com.artem.mi.spacexautenticom.ui.launchpad

sealed interface LaunchpadEffect {

    fun navigate(navigator: LaunchpadNavigator)

    data class NavigateToDetail(private val siteId: String) : LaunchpadEffect {
        override fun navigate(navigator: LaunchpadNavigator) {
            navigator.navigateToDetail(siteId)
        }
    }
}