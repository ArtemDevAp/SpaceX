package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.core.view.isVisible
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.google.android.material.progressindicator.CircularProgressIndicator

sealed interface LaunchpadViewModelState {

    fun apply(
        progress: CircularProgressIndicator,
        toast: Show,
        loadList: ShowList
    ) = Unit

    data object Progress : LaunchpadViewModelState {
        override fun apply(
            progress: CircularProgressIndicator,
            toast: Show,
            loadList: ShowList
        ) {
            progress.isVisible = true
        }
    }

    data class Data(
        private val launchpads: List<LaunchpadData>
    ) : LaunchpadViewModelState {
        override fun apply(
            progress: CircularProgressIndicator,
            toast: Show,
            loadList: ShowList
        ) {
            progress.isVisible = false
            loadList.load(launchpads)
        }
    }

    data class Error(private val error: String = "") : LaunchpadViewModelState {
        override fun apply(
            progress: CircularProgressIndicator,
            toast: Show,
            loadList: ShowList
        ) {
            progress.isVisible = false
            toast.show(error)
        }
    }
}