package com.artem.mi.spacexautenticom.domain.services

import kotlinx.coroutines.flow.Flow

interface ConnectivityService {
    val observeNetworkStatus: Flow<Boolean>
}