package dev.kiyo9w.kmpboilerplate.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.kiyo9w.kmpboilerplate.core.ThemeTokens

private val Double.tokenDp: Dp get() = toFloat().dp

val ThemeSpaceXs: Dp = ThemeTokens.spaceXs.tokenDp
val ThemeSpaceSm: Dp = ThemeTokens.spaceSm.tokenDp
val ThemeSpaceMd: Dp = ThemeTokens.spaceMd.tokenDp
val ThemeSpaceLg: Dp = ThemeTokens.spaceLg.tokenDp
val ThemeSpaceXl: Dp = ThemeTokens.spaceXl.tokenDp
val ThemeRadiusMd: Dp = ThemeTokens.radiusMd.tokenDp
val ThemeTapMin: Dp = ThemeTokens.tapMin.tokenDp
