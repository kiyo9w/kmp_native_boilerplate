package dev.kiyo9w.kmpboilerplate.platform

/**
 * Device-adjacent template: official Android PackageManager + official iOS Bundle.
 * Copy this shape for the next phone API (locale, vibration, file write).
 *
 * Shared code sees only this interface. Android actual stays out of iOS compilation.
 */
data class AppVersion(
    val name: String,
    val build: String,
) {
    val label: String = "$name ($build)"
}

interface AppVersionReader {
    fun current(): AppVersion
}
