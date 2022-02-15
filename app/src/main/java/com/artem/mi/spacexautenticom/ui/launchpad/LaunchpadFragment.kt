package com.artem.mi.spacexautenticom.ui.launchpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.artem.mi.spacexautenticom.databinding.LauchpadFragmentBinding
import com.artem.mi.spacexautenticom.ext.safeNavigateFromNavController
import com.artem.mi.spacexautenticom.ext.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchpadFragment : Fragment() {

    private val viewModel: LaunchpadViewModel by viewModels()

    private var _binding: LauchpadFragmentBinding? = null

    private val binding get() = _binding!!

    private val launchpadAdapter by lazy {
        LaunchpadAdapter { suiteId ->
            safeNavigateFromNavController(
                LaunchpadFragmentDirections.actionLaunchpadFragmentToLaunchpadDetailFragment(
                    suiteId
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LauchpadFragmentBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.launchpadRecyclerView.run {
            setHasFixedSize(true)
            adapter = launchpadAdapter
        }

        viewModel.launchpadsData.observe(viewLifecycleOwner, { state ->

            if (state.error != null) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }

            binding.progressLoad.setVisible(state.launchpads.isEmpty() && state.error == null)

            launchpadAdapter.submitList(state.launchpads)
        })

    }

}
