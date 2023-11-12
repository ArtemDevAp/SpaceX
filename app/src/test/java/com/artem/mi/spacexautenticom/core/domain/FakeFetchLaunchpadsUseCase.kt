package com.artem.mi.spacexautenticom.core.domain

import com.artem.mi.spacexautenticom.domain.launchpads.FetchLaunchpadsUseCase
import com.artem.mi.spacexautenticom.domain.launchpads.Launchpads
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow

internal sealed interface FakeLaunchpadState {
    fun receiveResult(): Launchpads

    data class Base(private val launchpads: Launchpads) : FakeLaunchpadState {
        override fun receiveResult(): Launchpads = launchpads
    }

    data class ThrowError(private val exception: Exception) : FakeLaunchpadState {
        override fun receiveResult(): Launchpads {
            throw exception
        }
    }
}

internal class FakeFetchLaunchpadsUseCase : FetchLaunchpadsUseCase {
    private val mutableLaunchpads = Channel<FakeLaunchpadState>()
    override val invoke: Flow<Launchpads> =
        mutableLaunchpads.receiveAsFlow().map { it.receiveResult() }

    suspend fun emit(launchpads: FakeLaunchpadState) = mutableLaunchpads.send(launchpads)
}