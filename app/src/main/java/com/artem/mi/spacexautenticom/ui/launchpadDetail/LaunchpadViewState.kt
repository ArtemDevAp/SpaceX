package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.google.android.material.textview.MaterialTextView

sealed interface LaunchpadViewState {

    fun apply(
        fullName: MaterialTextView,
        status: MaterialTextView,
        lat: MaterialTextView,
        lng: MaterialTextView
    ) = Unit

    data object Loading : LaunchpadViewState

    data class Data(
        private val fullName: String,
        private val status: String,
        private val lat: String,
        private val lng: String
    ) : LaunchpadViewState {
        override fun apply(
            fullName: MaterialTextView,
            status: MaterialTextView,
            lat: MaterialTextView,
            lng: MaterialTextView
        ) {
            fullName.text = this.fullName
            status.text = this.status
            lat.text = this.lat
            lng.text = this.lng
        }
    }

    data object Error : LaunchpadViewState
}
