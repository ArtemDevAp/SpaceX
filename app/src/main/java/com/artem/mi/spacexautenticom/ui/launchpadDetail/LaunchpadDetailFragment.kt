package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.artem.mi.spacexautenticom.databinding.LaunchpadDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchpadDetailFragment : Fragment() {

    @Inject
    lateinit var factory: LaunchpadDetailViewModel.LaunchpadDetailViewModelHiltFactory

    private val args: LaunchpadDetailFragmentArgs by navArgs()

    private val viewModel: LaunchpadDetailViewModel by viewModels {
        LaunchpadDetailViewModel.provideFactory(factory, args.siteId)
    }

    private var _binding: LaunchpadDetailFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LaunchpadDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailLaunchpad.observe(viewLifecycleOwner, { detail ->
            binding.fullName.text = detail.site_name_long
            binding.status.text = detail.status
            binding.localizationLatitude.text = detail.location.latitude.toString()
            binding.localizationLongitude.text = detail.location.longitude.toString()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
