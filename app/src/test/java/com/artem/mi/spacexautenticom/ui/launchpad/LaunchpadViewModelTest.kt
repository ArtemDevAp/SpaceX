package com.artem.mi.spacexautenticom.ui.launchpad

import com.artem.mi.spacexautenticom.core.MainDispatcherRule
import com.artem.mi.spacexautenticom.core.repository.FakeFetchLaunchpadsRepository
import com.artem.mi.spacexautenticom.model.LaunchpadData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class LaunchpadViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeLaunchpadRepository = FakeFetchLaunchpadsRepository()
    private val launchpadViewModel = LaunchpadViewModel(fakeLaunchpadRepository)

    private val mockLaunchpads = listOf(
        LaunchpadData(
            siteId = "ID",
            siteNameLong = "nameLong"
        ),
        LaunchpadData(
            siteId = "ID1",
            siteNameLong = "nameLong1"
        )
    )

    @Test
    fun `give launchpads, when call init, then expect success load list`() = runTest {
        // Given
        fakeLaunchpadRepository.setLaunchpads(mockLaunchpads)
        // When
        launchpadViewModel.init(true)
        // Then
        val actual = launchpadViewModel.launchpadsData.value
        val expected = LaunchpadViewModelState.Data(mockLaunchpads)
        assertEquals(expected, actual)
    }

    @Test
    fun `give IOException, when call init, then expect load list`() = runTest {
        // Given
        val ioException = IOException()
        fakeLaunchpadRepository.setError(IOException())
        // When
        launchpadViewModel.init(true)
        // Then
        val actual = launchpadViewModel.launchpadsData.value
        val expected = LaunchpadViewModelState.Error(ioException.localizedMessage ?: "string res")
        assertEquals(expected, actual)
    }
}