package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artem.mi.spacexautenticom.R
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchpadFragmentTest : TestCase() {

    @Test
    fun testNavigationToLaunchpadDetailFragment() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val selectScenario = launchFragmentInContainer<LaunchpadFragment>()

        selectScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Thread.sleep(1000)

        onView(withId(R.id.launchpad_recycler_view))
            .perform(actionOnItemAtPosition<LaunchpadAdapter.ViewHolder>(0, click()))
    }


}