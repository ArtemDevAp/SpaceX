package com.artem.mi.spacexautenticom.cache

import android.util.Log
import com.artem.mi.spacexautenticom.model.LaunchpadDetailData

object LaunchpadDetailCache {

    private val launchpadDetailData: HashMap<String, LaunchpadDetailData> = HashMap()

    fun getLaunchpadDetail(suiteId: String): LaunchpadDetailData? =
        launchpadDetailData[suiteId]

    fun setLaunchpadDetail(suiteId: String, launchpadDetailData: LaunchpadDetailData) {
        Log.v("APP", "set data preload")
        this.launchpadDetailData[suiteId] = launchpadDetailData
    }

}