package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.navigation.NavController
import javax.inject.Inject

class LaunchpadNavigator @Inject constructor(
    private val navController: NavController
) {
    fun navigateToDetail(suiteId: String) = navController.navigate(
        LaunchpadFragmentDirections.actionLaunchpadFragmentToLaunchpadDetailFragment(suiteId)
    )
}