package dev.kiyo9w.kmpboilerplate.core

/**
 * Three product flavors. Debug, staging, and prod share the public dog.ceo URL today.
 * Change [Flavor.catalogBaseUrl] when a second environment exists. Skip BuildKonfig
 * until that second URL is real.
 *
 * Select the flavor in each app at `initKoin` (Android `BuildConfig.APP_ENVIRONMENT`,
 * iOS `APP_ENVIRONMENT` in Config.xcconfig). Override locally with
 * `-Papp.environment=staging`.
 */
enum class AppEnvironment {
    Debug,
    Staging,
    Prod,
}

data class Flavor(
    val environment: AppEnvironment,
    val catalogBaseUrl: String,
) {
    companion object {
        const val DEFAULT_CATALOG_BASE_URL: String = "https://dog.ceo/api"

        val Debug: Flavor = Flavor(AppEnvironment.Debug, DEFAULT_CATALOG_BASE_URL)
        val Staging: Flavor = Flavor(AppEnvironment.Staging, DEFAULT_CATALOG_BASE_URL)
        val Prod: Flavor = Flavor(AppEnvironment.Prod, DEFAULT_CATALOG_BASE_URL)

        fun fromName(name: String): Flavor {
            return when (name.lowercase()) {
                "staging", "stg" -> Staging
                "prod", "production", "release" -> Prod
                else -> Debug
            }
        }
    }
}

object AppConfig {
    const val catalogPageSize: Int = 12
    const val databaseName: String = "kmp_boilerplate.db"
}
