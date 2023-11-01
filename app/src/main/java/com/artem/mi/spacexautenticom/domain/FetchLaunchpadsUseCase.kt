package com.artem.mi.spacexautenticom.domain

import com.artem.mi.spacexautenticom.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.artem.mi.spacexautenticom.network.ConnectivityService
import com.artem.mi.spacexautenticom.repository.LaunchpadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

data class Launchpads(val launchpads: List<LaunchpadData>)

interface FetchLaunchpadsUseCase {
    val invoke: Flow<Launchpads>
}

class FetchLaunchpadsUseCaseImpl @Inject constructor(
    connectivityService: ConnectivityService,
    private val launchpadRepository: LaunchpadRepository,
    private val launchpadLocalCache: LaunchpadCache
) : FetchLaunchpadsUseCase {
    override val invoke = connectivityService.observeNetworkStatus
        .mapLatest { networkAvailable ->
            val launchpads =
                if (!networkAvailable) launchpadLocalCache.getAll()
                else launchpadRepository.fetchLaunchpads()

            Launchpads(launchpads)
        }
}