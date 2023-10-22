package com.artem.mi.spacexautenticom.ui.launchpadDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.repository.LaunchpadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchpadDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val launchpadRepository: LaunchpadRepository,
    private val uiMapper: LaunchpadDetailUiMapper
) : ViewModel() {

    private val siteId = savedStateHandle.get<String>(SITE_ID_KEY)

    private val mutableDetailLaunchpad =
        MutableStateFlow<LaunchpadViewState>(LaunchpadViewState.Loading)
    val detailLaunchpad = mutableDetailLaunchpad.asStateFlow()

    fun init(firstStart: Boolean) {
        if (!firstStart) return
        viewModelScope.launch {
            require(siteId != null) { "LaunchpadDetailViewModel must contain siteId extra" }
            val result = launchpadRepository.fetchDetailLaunchpad(siteId)
            val detailUi = uiMapper.map(result)
            mutableDetailLaunchpad.update { detailUi }
        }
    }

    private companion object {
        const val SITE_ID_KEY = "siteId"
    }
}