package dev.kiyo9w.kmpboilerplate.core

/**
 * Build-time flavor stand-in. Wire real product flavors / Xcode schemes later.
 * Debug and release share one API here so a first clone runs without secrets.
 */
enum class AppEnvironment {
    Debug,
    Release,
}

object AppConfig {
    val environment: AppEnvironment = if (isDebugBuild()) AppEnvironment.Debug else AppEnvironment.Release
    const val catalogBaseUrl: String = "https://dog.ceo/api"
    const val catalogPageSize: Int = 12
    const val databaseName: String = "kmp_boilerplate.db"
}

expect fun isDebugBuild(): Boolean
