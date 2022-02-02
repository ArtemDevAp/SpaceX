package com.artem.mi.spacexautenticom.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun Fragment.safeNavigateFromNavController(directions: NavDirections) {
    val navController = findNavController()
    when (val destination = navController.currentDestination) {
        is FragmentNavigator.Destination -> {
            if (javaClass.name == destination.className) {
                navController.navigate(directions)
            }
        }
        is DialogFragmentNavigator.Destination -> {
            if (javaClass.name == destination.className) {
                navController.navigate(directions)
            }
        }
    }
}