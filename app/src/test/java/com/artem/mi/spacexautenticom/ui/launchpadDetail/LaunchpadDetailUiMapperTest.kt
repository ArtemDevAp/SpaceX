package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.artem.mi.spacexautenticom.model.ApiResponse
import com.artem.mi.spacexautenticom.model.ErrorResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class LaunchpadDetailUiMapperTest {

    private val mapper = LaunchpadDetailUiMapperImpl()

    @Test
    fun `success input, expect data view state`() {
        // Given
        val input = ApiResponse.Success(launchpadDetail)

        // When
        val actual = mapper.map(input)

        // Then
        val expected = launchpadDataState
        assertEquals(expected, actual)
    }

    @Test
    fun `error input, expect error view state`() {
        // Given
        val input = ApiResponse.Error(ErrorResponse(""))

        // When
        val actual = mapper.map(input)

        // Then
        val expected = LaunchpadViewState.Error
        assertEquals(expected, actual)
    }
}