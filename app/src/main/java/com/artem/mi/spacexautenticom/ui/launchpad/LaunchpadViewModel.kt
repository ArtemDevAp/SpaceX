package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.core.throttleLatest
import com.artem.mi.spacexautenticom.repository.LaunchpadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchpadViewModel @Inject constructor(
    private val launchpadRepo: LaunchpadRepository
) : ViewModel() {

    private val _launchpadsData: MutableStateFlow<LaunchpadViewModelState> =
        MutableStateFlow(LaunchpadViewModelState.Progress)
    val launchpadsData: StateFlow<LaunchpadViewModelState> = _launchpadsData.asStateFlow()

    private val effectChanel: Channel<LaunchpadEffect> = Channel()
    val effect = effectChanel.receiveAsFlow().throttleLatest(EFFECT_DELAY)

    fun init(firstStart: Boolean) {
        if (!firstStart) return
        viewModelScope.launch {
            try {
                val launchpads = launchpadRepo.fetchLaunchpads()
                _launchpadsData.update { LaunchpadViewModelState.Data(launchpads = launchpads) }
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (e: Exception) {
                _launchpadsData.update {
                    LaunchpadViewModelState.Error(e.localizedMessage ?: "string res")
                }
            }
        }
    }

    fun onItemSelected(itemId: String) {
        effectChanel.trySend(LaunchpadEffect.NavigateToDetail(itemId))
    }

    private companion object {
        const val EFFECT_DELAY = 1000L
    }
}