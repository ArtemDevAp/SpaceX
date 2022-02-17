package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.artem.mi.spacexautenticom.databinding.LaunchpadDetailFragmentBinding
import com.artem.mi.spacexautenticom.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchpadDetailFragment : BaseFragment<LaunchpadDetailFragmentBinding>() {

    @Inject
    lateinit var factory: LaunchpadDetailViewModel.LaunchpadDetailViewModelHiltFactory

    private val args: LaunchpadDetailFragmentArgs by navArgs()

    private val viewModel: LaunchpadDetailViewModel by viewModels {
        LaunchpadDetailViewModel.provideFactory(factory, args.siteId)
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LaunchpadDetailFragmentBinding =
        LaunchpadDetailFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailLaunchpad.observe(viewLifecycleOwner, { detail ->
            binding.fullName.text = detail.site_name_long
            binding.status.text = detail.status
            binding.localizationLatitude.text = detail.location!!.latitude.toString()
            binding.localizationLongitude.text = detail.location!!.longitude.toString()
        })

    }

}
