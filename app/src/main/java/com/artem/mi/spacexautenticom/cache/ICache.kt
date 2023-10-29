package com.artem.mi.spacexautenticom.cache

interface ICache<T> {

    val isExpired: Boolean

    val isEmpty: Boolean

    fun add(item: T)

    fun addAll(items: List<T>)

    fun get(index: Int): T?

    fun remove(item: T)

    fun clear()

    fun getAll(): List<T>
}