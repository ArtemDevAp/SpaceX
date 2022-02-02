package com.artem.mi.spacexautenticom.model

sealed class ApiResponse<out T> {

    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val errorResponse: ErrorResponse) : ApiResponse<Nothing>()

}
