package com.artem.mi.spacexautenticom.model

sealed class ApiResponse<out T, out E> {

    data class Success<T>(val data: T) : ApiResponse<T, Nothing>()
    data class Error<E>(val errorResponse: E) : ApiResponse<Nothing, E>()

}
