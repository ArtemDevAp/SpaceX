package com.artem.mi.spacexautenticom.data.cache

import com.artem.mi.spacexautenticom.data.network.LaunchpadData

class LaunchpadCache : ListCache<LaunchpadData> {

    private var updateTime: Long = 0L

    companion object {
        private const val lifeTime: Long = 10_000L
    }

    private val launchpadDetailData = mutableListOf<LaunchpadData>()

    override val isEmpty: Boolean
        get() = launchpadDetailData.isEmpty()

    override fun get(index: Int): LaunchpadData? {
        return launchpadDetailData.elementAtOrNull(index)
    }

    override val isExpired: Boolean
        get() = (System.currentTimeMillis() - updateTime) > lifeTime

    override fun add(item: LaunchpadData) {
        launchpadDetailData += item
        updateTime = System.currentTimeMillis()
    }

    override fun remove(item: LaunchpadData) {
        launchpadDetailData -= item
    }

    override fun clear() {
        launchpadDetailData.clear()
    }

    override fun addAll(items: List<LaunchpadData>) {
        clear()
        launchpadDetailData.addAll(items)
        updateTime = System.currentTimeMillis()
    }

    override fun getAll(): List<LaunchpadData> {
        return launchpadDetailData
    }
}