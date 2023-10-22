package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.databinding.LaunchpadDetailFragmentBinding
import com.artem.mi.spacexautenticom.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LaunchpadDetailFragment : Fragment(R.layout.launchpad_detail_fragment) {

    private val binding by viewBinding(LaunchpadDetailFragmentBinding::bind)

    @Inject
    lateinit var factory: LaunchpadDetailViewModel.LaunchpadDetailViewModelHiltFactory

    private val args: LaunchpadDetailFragmentArgs by navArgs()
    private val viewModel: LaunchpadDetailViewModel by viewModels {
        LaunchpadDetailViewModel.provideFactory(factory, args.siteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
}