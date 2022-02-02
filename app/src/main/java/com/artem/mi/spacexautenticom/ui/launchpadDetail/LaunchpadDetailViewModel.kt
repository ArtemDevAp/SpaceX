package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import kotlinx.coroutines.launch

class LaunchpadDetailViewModel(
    private val siteId: String?
) : ViewModel() {

    private val launchpadRepo = LaunchpadRepo()

    private val _detailLaunchpad: MutableLiveData<LaunchpadDetailData> =
        MutableLiveData()
    val detailLaunchpad: LiveData<LaunchpadDetailData> = _detailLaunchpad

    init {
        viewModelScope.launch {
            siteId?.let { siteId ->
                when (val result = launchpadRepo.fetchDetailLaunchpad(siteId)) {
                    is ApiResponse.Success -> {
                        result.data.let { detail ->
                            _detailLaunchpad.postValue(detail)
                        }
                    }
                    is ApiResponse.Error -> {
                        Log.v("APP", "error ${result.errorResponse.error}")
                    }
                }
            }
        }
    }
}