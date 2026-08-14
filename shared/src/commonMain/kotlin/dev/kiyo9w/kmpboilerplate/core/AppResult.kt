package dev.kiyo9w.kmpboilerplate.core

sealed class AppResult<out T> {
    data class Ok<T>(val value: T) : AppResult<T>()
    data class Err(val message: String, val cause: Throwable? = null) : AppResult<Nothing>()

    val isOk: Boolean get() = this is Ok
}

inline fun <T> AppResult<T>.getOrNull(): T? = (this as? AppResult.Ok)?.value
