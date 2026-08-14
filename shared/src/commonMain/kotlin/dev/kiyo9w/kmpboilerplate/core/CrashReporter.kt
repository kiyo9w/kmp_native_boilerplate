package dev.kiyo9w.kmpboilerplate.core

/**
 * Hook for Firebase Crashlytics / Sentry. Default is a no-op so a clone runs offline.
 * Bind a real reporter from androidApp / iosApp when you add those SDKs.
 */
interface CrashReporter {
    fun record(throwable: Throwable, extras: Map<String, String> = emptyMap())
    fun breadcrumb(message: String)
}

class NoOpCrashReporter : CrashReporter {
    override fun record(throwable: Throwable, extras: Map<String, String>) {
        AppLog.e("crash (noop)", throwable)
    }

    override fun breadcrumb(message: String) {
        AppLog.d("breadcrumb: $message")
    }
}
