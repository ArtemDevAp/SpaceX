package com.artem.mi.spacexautenticom.ui.launchpadDetail

import com.artem.mi.spacexautenticom.domain.core.SPXResult
import com.artem.mi.spacexautenticom.data.network.ErrorResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class LaunchpadDetailUiMapperTest {

    private val mapper = LaunchpadDetailUiMapperImpl()

    @Test
    fun `success input, expect data view state`() {
        // Given
        val input = SPXResult.Success(launchpadDetail)

        // When
        val actual = mapper.map(input)

        // Then
        val expected = launchpadDataState
        assertEquals(expected, actual)
    }

    @Test
    fun `error input, expect error view state`() {
        // Given
        val input = SPXResult.Error(ErrorResponse(""))

        // When
        val actual = mapper.map(input)

        // Then
        val expected = LaunchpadViewState.Error
        assertEquals(expected, actual)
    }
}