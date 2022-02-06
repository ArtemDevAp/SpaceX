package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchpadViewModel @Inject constructor(
    launchpadRepo: LaunchpadRepo
) : ViewModel() {

    private val _launchpadsData: MutableLiveData<LaunchpadViewModelState> = MutableLiveData()
    val launchpadsData: LiveData<LaunchpadViewModelState> = _launchpadsData

    init {
        viewModelScope.launch {
            runCatching {
                launchpadRepo.fetchLaunchpads()
            }.onSuccess { launchpads ->
                _launchpadsData.postValue(LaunchpadViewModelState(launchpads = launchpads))
            }.onFailure {
                _launchpadsData.postValue(LaunchpadViewModelState(error = it.localizedMessage))
            }
        }
    }
}

