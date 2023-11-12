package com.artem.mi.spacexautenticom.ui.launchpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.databinding.LauchpadFragmentBinding
import com.artem.mi.spacexautenticom.ui.core.ClickListener
import com.artem.mi.spacexautenticom.ui.core.collectWithLifecycleState
import com.artem.mi.spacexautenticom.ui.core.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LaunchpadFragment : Fragment(R.layout.lauchpad_fragment), Show {

    @Inject
    lateinit var navigator: LaunchpadNavigator
    private val binding by viewBinding(LauchpadFragmentBinding::bind)
    private val viewModel: LaunchpadViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val launchpadAdapter = LaunchpadAdapter(
            object : ClickListener<String> {
                override fun onClick(param: String) {
                    viewModel.onItemSelected(param)
                }
            }
        )

        binding.launchpadRecyclerView.run {
            setHasFixedSize(true)
            adapter = launchpadAdapter
        }

        collectWithLifecycleState(Lifecycle.State.STARTED) {
            launch { state(showList = launchpadAdapter) }
            launch { trigger() }
        }
    }

    private suspend fun state(showList: ShowList) {
        viewModel.launchpadState.collectLatest { state ->
            state.apply(
                progress = binding.progressLoad,
                informationText = binding.informationText,
                recyclerView = binding.launchpadRecyclerView,
                toast = this@LaunchpadFragment,
                loadList = showList
            )
        }
    }

    private suspend fun trigger() {
        viewModel.effect.collectLatest {
            it.navigate(navigator)
        }
    }

    override fun show(@StringRes msg: Int) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}