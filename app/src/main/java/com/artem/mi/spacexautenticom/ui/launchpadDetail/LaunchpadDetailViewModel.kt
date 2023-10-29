package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.repository.LaunchpadRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LaunchpadDetailViewModel @AssistedInject constructor(
    @Assisted private val siteId: String,
    private val launchpadRepo: LaunchpadRepository,
    private val uiMapper: LaunchpadDetailUiMapper
) : ViewModel() {

    private val _detailLaunchpad = MutableStateFlow<LaunchpadViewState>(LaunchpadViewState.Loading)
    val detailLaunchpad = _detailLaunchpad.asStateFlow()

    fun init(bundle: Bundle?) {
        if (bundle != null) return
        viewModelScope.launch {
            siteId.let { siteId ->
                val result = launchpadRepo.fetchDetailLaunchpad(siteId)
                val detailUi = uiMapper.map(result)
                _detailLaunchpad.update { detailUi }
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