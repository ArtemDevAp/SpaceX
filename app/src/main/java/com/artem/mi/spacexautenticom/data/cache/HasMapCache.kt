package com.artem.mi.spacexautenticom.data.cache

interface HasMapCache<T> {

    val isExpired: Boolean

    val isEmpty: Boolean

    fun hasKey(key: String): Boolean

    fun get(key: String): T?

    fun add(key: String, item: T)

    fun remove(key: String)

    fun clear()
}