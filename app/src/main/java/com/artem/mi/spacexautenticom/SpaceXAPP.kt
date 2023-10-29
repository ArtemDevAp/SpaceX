package com.artem.mi.spacexautenticom

import javax.inject.Inject
import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.artem.mi.spacexautenticom.repository.LaunchpadRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@HiltAndroidApp
class SpaceXAPP : Application(), LifecycleEventObserver {

    @Inject
    lateinit var launchpadRepo: LaunchpadRepository

    private val scope = CoroutineScope(SupervisorJob() + IO)

    override fun onCreate() {
        super.onCreate()
        scope.launch {
            runCatching {
                launchpadRepo.fetchLaunchpads()
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> scope.coroutineContext.cancel()
            else -> {
            }
        }
    }
}