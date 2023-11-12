package com.artem.mi.spacexautenticom.domain.launchpads

import com.artem.mi.spacexautenticom.data.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.artem.mi.spacexautenticom.domain.services.ConnectivityService
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