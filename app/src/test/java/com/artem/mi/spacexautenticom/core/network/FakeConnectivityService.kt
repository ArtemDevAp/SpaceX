package com.artem.mi.spacexautenticom.core.network

import com.artem.mi.spacexautenticom.domain.services.ConnectivityService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeConnectivityService : ConnectivityService {
    private val isConnected = MutableSharedFlow<Boolean>()
    override val observeNetworkStatus: Flow<Boolean> = isConnected
    suspend fun emit(connected: Boolean) = isConnected.emit(connected)
}