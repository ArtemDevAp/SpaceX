package com.artem.mi.spacexautenticom.ui.launchpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.databinding.LauchpadFragmentBinding
import com.artem.mi.spacexautenticom.ext.safeNavigateFromNavController
import com.artem.mi.spacexautenticom.ext.setVisible
import com.artem.mi.spacexautenticom.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchpadFragment : Fragment(R.layout.lauchpad_fragment) {

    private val binding by viewBinding(LauchpadFragmentBinding::bind)

    private val viewModel: LaunchpadViewModel by viewModels()

    private val launchpadAdapter by lazy {
        LaunchpadAdapter { suiteId ->
            safeNavigateFromNavController(
                LaunchpadFragmentDirections.actionLaunchpadFragmentToLaunchpadDetailFragment(
                    suiteId
                )
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.launchpadRecyclerView.run {
            setHasFixedSize(true)
            adapter = launchpadAdapter
        }

        viewModel.launchpadsData.observe(viewLifecycleOwner) { state ->

            if (state.error != null) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }

            binding.progressLoad.setVisible(state.launchpads.isEmpty() && state.error == null)

            launchpadAdapter.submitList(state.launchpads)
        }
    }
}