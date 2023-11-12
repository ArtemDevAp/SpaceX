package com.artem.mi.spacexautenticom.data.cache

import com.artem.mi.spacexautenticom.data.network.LaunchpadDetailData

class LaunchpadDetailCache : HasMapCache<LaunchpadDetailData> {

    private var updateTime: Long = 0L

    companion object {
        private const val lifeTime: Long = 10_000L
    }

    private val launchpadDetailData: HashMap<String, LaunchpadDetailData> = HashMap()

    override val isExpired: Boolean
        get() = (System.currentTimeMillis() - updateTime) > lifeTime

    override val isEmpty: Boolean
        get() = launchpadDetailData.isEmpty()

    override fun get(key: String): LaunchpadDetailData? {
        return launchpadDetailData[key]
    }

    override fun add(key: String, item: LaunchpadDetailData) {
        launchpadDetailData[key] = item
        updateTime = System.currentTimeMillis()
    }

    override fun remove(key: String) {
        launchpadDetailData.remove(key)
    }

    override fun clear() {
        launchpadDetailData.clear()
    }

    override fun hasKey(key: String): Boolean {
        return launchpadDetailData.containsKey(key)
    }
}