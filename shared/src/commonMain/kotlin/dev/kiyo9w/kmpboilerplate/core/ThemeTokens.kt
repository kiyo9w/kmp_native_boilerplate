package dev.kiyo9w.kmpboilerplate.core

/**
 * Shared visual scale. Apps map these numbers onto Compose dp and SwiftUI points.
 * Colors stay on the platform theme (Material3 / SwiftUI semantic).
 */
object ThemeTokens {
    const val spaceXs: Double = 4.0
    const val spaceSm: Double = 8.0
    const val spaceMd: Double = 16.0
    const val spaceLg: Double = 24.0
    const val spaceXl: Double = 32.0

    const val radiusSm: Double = 8.0
    const val radiusMd: Double = 12.0
    const val radiusLg: Double = 16.0

    const val tapMin: Double = 48.0

    const val durationFastMs: Long = 150
    const val durationNormalMs: Long = 250
}
