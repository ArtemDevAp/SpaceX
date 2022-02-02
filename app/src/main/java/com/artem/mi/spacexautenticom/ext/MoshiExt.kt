package com.artem.mi.spacexautenticom.ext

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

inline fun <reified T> Moshi.parseErrorBody(errorBody: ResponseBody?): T? {
    return try {
        errorBody?.source()?.let {
            this.adapter(T::class.java).fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}