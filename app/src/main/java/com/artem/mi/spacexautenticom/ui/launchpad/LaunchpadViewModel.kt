package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.core.throttleLatest
import com.artem.mi.spacexautenticom.domain.launchpads.FetchLaunchpadsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LaunchpadViewModel @Inject constructor(
    private val launchpadsToUI: LaunchpadToUI,
    fetchLaunchpadsUseCase: FetchLaunchpadsUseCase
) : ViewModel() {

    private val fetchLaunchpads =
        fetchLaunchpadsUseCase.invoke.map { domainLaunchpads ->
            launchpadsToUI.map(domainLaunchpads)
        }.catch {
            emit(LaunchpadUi.Error(R.string.something_went_wrong))
        }

    val launchpadState: StateFlow<LaunchpadUi> =
        fetchLaunchpads
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                LaunchpadUi.Progress
            )

    private val effectChanel: Channel<LaunchpadEffect> = Channel()
    val effect = effectChanel.receiveAsFlow().throttleLatest(EFFECT_DELAY)

    fun onItemSelected(itemId: String) {
        effectChanel.trySend(LaunchpadEffect.NavigateToDetail(itemId))
    }

    private companion object {
        const val EFFECT_DELAY = 1000L
    }
}