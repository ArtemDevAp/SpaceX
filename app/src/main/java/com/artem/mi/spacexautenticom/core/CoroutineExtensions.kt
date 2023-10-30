package com.artem.mi.spacexautenticom.core

import java.util.concurrent.CancellationException

inline fun <R> runCatchingSuspend(
    perform: () -> R,
    onError: (e: Exception) -> R
): R = try {
    perform.invoke()
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (e: Exception) {
    onError.invoke(e)
}