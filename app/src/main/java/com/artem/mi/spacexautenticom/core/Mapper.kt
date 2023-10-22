package com.artem.mi.spacexautenticom.core

interface Mapper<I, O> {
    fun map(input: I): O
}