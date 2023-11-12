package com.artem.mi.spacexautenticom.ui.launchpad

import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.core.MainDispatcherRule
import com.artem.mi.spacexautenticom.core.domain.FakeFetchLaunchpadsUseCase
import com.artem.mi.spacexautenticom.core.domain.FakeLaunchpadState
import com.artem.mi.spacexautenticom.core.spaceXTest
import com.artem.mi.spacexautenticom.domain.launchpads.Launchpads
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class LaunchpadViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchLaunchpadsUseCase = FakeFetchLaunchpadsUseCase()
    private val launchpadsToUI = LaunchpadToUI()
    private lateinit var viewModel: LaunchpadViewModel

    @Before
    fun setup() {
        viewModel = LaunchpadViewModel(
            launchpadsToUI = launchpadsToUI,
            fetchLaunchpadsUseCase = fetchLaunchpadsUseCase
        )
    }

    private val mockLaunchpads = FakeLaunchpadState.Base(
        Launchpads(
            listOf(
                LaunchpadData(
                    siteId = "ID",
                    siteNameLong = "nameLong"
                ),
                LaunchpadData(
                    siteId = "ID1",
                    siteNameLong = "nameLong1"
                )
            )
        )
    )

    @Test
    fun `when call launchpadState first time, expect progress state`() =
        runTest {
            spaceXTest(
                doOnStart = {
                    viewModel.launchpadState.collect()
                },
                assert = arrayOf({
                    val actual = LaunchpadUi.Progress
                    val expected = viewModel.launchpadState.value
                    assertEquals(expected, actual)
                }),
                given = {}
            )
        }

    @Test
    fun `given not empty launchpads, when fetch view model state, then expect Data state`() =
        runTest {
            spaceXTest(
                doOnStart = {
                    viewModel.launchpadState.collect()
                },
                given = {
                    fetchLaunchpadsUseCase.emit(mockLaunchpads)
                },
                assert = arrayOf({
                    val actual = viewModel.launchpadState.value
                    val expected = LaunchpadUi.Data(mockLaunchpads.receiveResult().launchpads)
                    assertEquals(expected, actual)
                })
            )
        }

    @Test
    fun `given empty launchpads, when fetch view model state, then expect InfoMessage states`() =
        runTest {
            spaceXTest(
                doOnStart = {
                    viewModel.launchpadState.collect()
                },
                given = {
                    val emptyLaunchpads = FakeLaunchpadState.Base(Launchpads(emptyList()))
                    fetchLaunchpadsUseCase.emit(emptyLaunchpads)
                },
                assert = arrayOf({
                    val actual = viewModel.launchpadState.value
                    val expected = LaunchpadUi.InfoMessage(R.string.waiting_for_network)
                    assertEquals(expected, actual)
                })
            )
        }

    @Test
    fun `given error during observe use case, when fetch view model state, then expect Error states`() =
        runTest {
            spaceXTest(
                doOnStart = {
                    viewModel.launchpadState.collect()
                },
                given = {
                    val errorLaunchpads = FakeLaunchpadState.ThrowError(IOException())
                    fetchLaunchpadsUseCase.emit(errorLaunchpads)
                },
                assert = arrayOf({
                    val actual = viewModel.launchpadState.value
                    val expected = LaunchpadUi.Error(R.string.something_went_wrong)
                    assertEquals(expected, actual)
                })
            )
        }

    @Test
    fun `given selected item id, call onItemSelected, expect NavigateToDetail`() = runTest {
        val selectedItemId = "1"
        val collectedStates = mutableListOf<LaunchpadEffect>()
        spaceXTest(
            doOnStart = {
                viewModel.effect.collectLatest {
                    collectedStates.add(it)
                }
            },
            given = {
                viewModel.onItemSelected(selectedItemId)
            },
            assert = arrayOf({
                assertEquals(
                    listOf(LaunchpadEffect.NavigateToDetail(selectedItemId)),
                    collectedStates
                )
            })
        )
    }

    @Test
    fun `given selected item id, call onItemSelected 3 times, expect NavigateToDetail once`() =
        runTest {
            val selectedItemId = "0"
            val selectedItemId1 = "1"
            val selectedItemId2 = "2"
            val collectedStates = mutableListOf<LaunchpadEffect>()
            spaceXTest(
                doOnStart = {
                    viewModel.effect.collectLatest {
                        collectedStates.add(it)
                    }
                },
                given = {
                    viewModel.onItemSelected(selectedItemId)
                    viewModel.onItemSelected(selectedItemId1)
                    viewModel.onItemSelected(selectedItemId2)
                },
                assert = arrayOf({
                    assertEquals(
                        listOf(LaunchpadEffect.NavigateToDetail(selectedItemId)),
                        collectedStates
                    )
                })
            )
        }
}