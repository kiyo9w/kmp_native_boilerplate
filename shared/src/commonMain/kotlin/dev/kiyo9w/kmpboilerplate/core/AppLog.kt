package dev.kiyo9w.kmpboilerplate.core

import co.touchlab.kermit.Logger

object AppLog {
    private val log = Logger.withTag("KmpBoilerplate")

    fun d(message: String) = log.d { message }
    fun i(message: String) = log.i { message }
    fun w(message: String, throwable: Throwable? = null) =
        if (throwable == null) log.w { message } else log.w(throwable) { message }
    fun e(message: String, throwable: Throwable? = null) =
        if (throwable == null) log.e { message } else log.e(throwable) { message }
}
