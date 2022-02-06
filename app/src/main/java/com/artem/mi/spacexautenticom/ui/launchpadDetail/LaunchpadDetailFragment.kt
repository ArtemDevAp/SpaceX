package com.artem.mi.spacexautenticom.ui.launchpadDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.artem.mi.spacexautenticom.R
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchpadDetailFragment : Fragment(R.layout.launchpad_detail_fragment) {

    @Inject
    lateinit var factory: LaunchpadDetailViewModel.LaunchpadDetailViewModelHiltFactory

    private val args: LaunchpadDetailFragmentArgs by navArgs()

    private val viewModel: LaunchpadDetailViewModel by viewModels{
        LaunchpadDetailViewModel.provideFactory(factory, args.siteId)
    }
       // LaunchpadDetailViewModel.provideFactory(factory, args.sieId)


    private lateinit var fullName: MaterialTextView
    private lateinit var status: MaterialTextView
    private lateinit var localizationLatitude: MaterialTextView
    private lateinit var localizationLongitude: MaterialTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullName = view.findViewById(R.id.full_name)
        status = view.findViewById(R.id.status)
        localizationLatitude = view.findViewById(R.id.localization_latitude)
        localizationLongitude = view.findViewById(R.id.localization_longitude)

        viewModel.detailLaunchpad.observe(viewLifecycleOwner, { detail ->
            fullName.text = detail.site_name_long
            status.text = detail.status
            localizationLatitude.text = detail.location.latitude.toString()
            localizationLongitude.text = detail.location.longitude.toString()
        })

    }

}
