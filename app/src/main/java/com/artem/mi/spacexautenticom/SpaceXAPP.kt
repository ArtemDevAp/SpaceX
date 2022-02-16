package com.artem.mi.spacexautenticom

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltAndroidApp
class SpaceXAPP : Application(), LifecycleEventObserver {

    @Inject
    lateinit var launchpadRepo: LaunchpadRepo

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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
