package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.util.Log
import androidx.lifecycle.*
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.net.UnknownHostException


class LaunchpadDetailViewModel @AssistedInject constructor(
    @Assisted private val siteId: String?,
    private val launchpadRepo: LaunchpadRepo
) : ViewModel() {


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

    @AssistedFactory
    interface LaunchpadDetailViewModelHiltFactory {
        fun create(suiteId: String?): LaunchpadDetailViewModel
    }


    companion object {
        fun provideFactory(
            launchpadDetailViewModelHiltFactory: LaunchpadDetailViewModelHiltFactory,
            suiteId: String?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LaunchpadDetailViewModel::class.java)) {
                    return launchpadDetailViewModelHiltFactory.create(suiteId) as T
                }

                throw UnknownHostException("unknown class cast")
            }

        }
    }

}