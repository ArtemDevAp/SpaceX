package com.artem.mi.spacexautenticom.ui.launchpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.ext.safeNavigateFromNavController
import com.artem.mi.spacexautenticom.ext.setVisible
import com.google.android.material.progressindicator.CircularProgressIndicator

class LaunchpadFragment : Fragment(R.layout.main_fragment) {

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

    private lateinit var progress: CircularProgressIndicator
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.launchpad_recycler_view)
        progress = view.findViewById(R.id.progress_load)

        recyclerView.adapter = launchpadAdapter
        recyclerView.setHasFixedSize(true)

        viewModel.launchpadsData.observe(viewLifecycleOwner, { state ->

            if (state.error != null) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }

            progress.setVisible(state.launchpads.isEmpty() && state.error == null)

            launchpadAdapter.submitList(state.launchpads)
        })
    }

}