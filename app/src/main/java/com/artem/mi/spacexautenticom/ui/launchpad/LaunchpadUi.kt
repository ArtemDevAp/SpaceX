package com.artem.mi.spacexautenticom.ui.launchpad

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.google.android.material.progressindicator.CircularProgressIndicator

sealed interface LaunchpadUi {

    fun apply(
        progress: CircularProgressIndicator,
        informationText: TextView,
        recyclerView: RecyclerView,
        toast: Show,
        loadList: ShowList
    )

    data object Progress : LaunchpadUi {
        override fun apply(
            progress: CircularProgressIndicator,
            informationText: TextView,
            recyclerView: RecyclerView,
            toast: Show,
            loadList: ShowList
        ) {
            progress.isVisible = true
        }
    }

    data class Data(private val launchpads: List<LaunchpadData>) : LaunchpadUi {
        override fun apply(
            progress: CircularProgressIndicator,
            informationText: TextView,
            recyclerView: RecyclerView,
            toast: Show,
            loadList: ShowList
        ) {
            loadList.load(launchpads)
            progress.isVisible = false
            informationText.visibility = GONE
            recyclerView.visibility = VISIBLE
        }
    }

    data class Error(@StringRes private val error: Int) : LaunchpadUi {
        override fun apply(
            progress: CircularProgressIndicator,
            informationText: TextView,
            recyclerView: RecyclerView,
            toast: Show,
            loadList: ShowList
        ) {
            progress.isVisible = false
            toast.show(error)
        }
    }

    data class InfoMessage(@StringRes private val message: Int) : LaunchpadUi {
        override fun apply(
            progress: CircularProgressIndicator,
            informationText: TextView,
            recyclerView: RecyclerView,
            toast: Show,
            loadList: ShowList
        ) {
            recyclerView.visibility = GONE
            progress.isVisible = true
            informationText.setText(message)
            informationText.visibility = VISIBLE
        }
    }
}