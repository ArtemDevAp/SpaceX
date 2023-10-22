package com.artem.mi.spacexautenticom.ui.launchpadDetail

import androidx.lifecycle.SavedStateHandle
import com.artem.mi.spacexautenticom.core.MainDispatcherRule
import com.artem.mi.spacexautenticom.core.repository.FakeFetchDetailLaunchpadRepository
import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.Location
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Assert.assertEquals
import org.junit.Test

class LaunchpadDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val location = Location(
        name = "",
        region = "",
        latitude = 0.0,
        longitude = 0.0
    )

    private val launchpadsRepository = FakeFetchDetailLaunchpadRepository()

    @Test
    fun `when is not first start, expect previous state`() {
        // Given
        val launchpadDetailViewModel = provideViewModel(null)

        // When
        launchpadDetailViewModel.init(false)

        // Then
        val actual = launchpadDetailViewModel.detailLaunchpad.value
        val expected = LaunchpadViewState.Loading
        assertEquals(expected, actual)
    }

    @Test
    fun `given detail launchpad with argument and is first start, call invoke, expect success state`() =
        runTest {
            // Given
            val launchpadDetailViewModel = provideViewModel("1")
            launchpadsRepository.setDetailLaunchpad(ApiResponse.Success(launchpadDetail))

            // When
            launchpadDetailViewModel.init(true)

            // Then
            val actual = launchpadDetailViewModel.detailLaunchpad.value
            val expected = LaunchpadViewState.Data(
                fullName = "",
                status = "",
                lat = location.latitude.toString(),
                lng = location.longitude.toString()
            )
            assertEquals(expected, actual)
        }

    @Test(expected = IllegalArgumentException::class)
    fun `given detail launchpad, argument did not pass and is first start, call init, expect error state`() =
        runTest {
            // Given
            val launchpadDetailViewModel = provideViewModel(null)
            launchpadsRepository.setDetailLaunchpad(ApiResponse.Success(launchpadDetail))

            // When
            launchpadDetailViewModel.init(true)

            // Then
            launchpadDetailViewModel.detailLaunchpad.value
        }

    private fun provideViewModel(argument: String?): LaunchpadDetailViewModel {
        return LaunchpadDetailViewModel(
            savedStateHandle = SavedStateHandle().apply {
                set(SITE_ID_KEY, argument)
            },
            launchpadRepository = launchpadsRepository,
            uiMapper = LaunchpadDetailUiMapperImpl()
        )
    }

    private companion object {
        const val SITE_ID_KEY = "siteId"
    }
}