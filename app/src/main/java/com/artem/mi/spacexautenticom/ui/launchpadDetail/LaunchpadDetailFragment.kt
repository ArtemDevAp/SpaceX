package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.databinding.LaunchpadDetailFragmentBinding
import com.artem.mi.spacexautenticom.ui.core.collectWithLifecycleState
import com.artem.mi.spacexautenticom.ui.core.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LaunchpadDetailFragment : Fragment(R.layout.launchpad_detail_fragment) {

    private val binding by viewBinding(LaunchpadDetailFragmentBinding::bind)

    private val viewModel: LaunchpadDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(savedInstanceState == null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectWithLifecycleState(Lifecycle.State.STARTED) {
            viewModel.detailLaunchpad.collectLatest { detail ->
                detail.apply(
                    fullName = binding.fullName,
                    status = binding.status,
                    lat = binding.localizationLatitude,
                    lng = binding.localizationLongitudeText
                )
            }
        }
    }
}