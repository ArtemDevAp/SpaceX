package com.artem.mi.spacexautenticom.ui.launchpad

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchpadViewModel @Inject constructor(
    private val launchpadRepo: LaunchpadRepo
) : ViewModel() {

    private val _launchpadsData: MutableLiveData<LaunchpadViewModelState> = MutableLiveData()
    val launchpadsData: LiveData<LaunchpadViewModelState> = _launchpadsData

    fun init(savedStateHandle: Bundle?) {
        if (savedStateHandle != null) return
        viewModelScope.launch {
            try {
                val launchpads = launchpadRepo.fetchLaunchpads()
                _launchpadsData.postValue(LaunchpadViewModelState(launchpads = launchpads))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _launchpadsData.postValue(LaunchpadViewModelState(error = e.localizedMessage))
            }
        }
    }

}

