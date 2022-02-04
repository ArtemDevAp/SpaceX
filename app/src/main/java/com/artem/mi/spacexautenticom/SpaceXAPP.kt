package com.artem.mi.spacexautenticom

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import kotlinx.coroutines.*

class SpaceXAPP(
    //inject repo
    //private var preload : List<IPreload>
) : Application(), LifecycleEventObserver {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        scope.launch {
            runCatching {
//                preload.forEach {
//                    it.run()
//                }
                LaunchpadRepo.fetchLaunchpads()
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