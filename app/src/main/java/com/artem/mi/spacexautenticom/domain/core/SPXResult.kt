package com.artem.mi.spacexautenticom.domain.core

sealed class SPXResult<out T, out E> {
    data class Success<T>(val data: T) : SPXResult<T, Nothing>()
    data class Error<E>(val errorResponse: E) : SPXResult<Nothing, E>()
}