package com.socialai.app.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Primary SocialSolve AI Brand Colors
val PrimaryIndigo = Color(0xFF1E40AF)
val PrimaryIndigoContainer = Color(0xFFDBEAFE)

val SecondaryTeal = Color(0xFF0D9488)
val SecondaryTealContainer = Color(0xFFCCFBF1)

val AccentAmber = Color(0xFFD97706)
val AccentAmberContainer = Color(0xFFFEF3C7)

val StatusGreen = Color(0xFF059669)
val StatusGreenContainer = Color(0xFFD1FAE5)

val StatusRose = Color(0xFFE11D48)
val StatusRoseContainer = Color(0xFFFFE4E6)

val SlateBackgroundLight = Color(0xFFF8FAFC)
val SlateSurfaceLight = Color(0xFFFFFFFF)

val SlateBackgroundDark = Color(0xFF0F172A)
val SlateSurfaceDark = Color(0xFF1E293B)

val LightColorScheme = lightColorScheme(
    primary = PrimaryIndigo,
    onPrimary = Color.White,
    primaryContainer = PrimaryIndigoContainer,
    onPrimaryContainer = Color(0xFF1E3A8A),
    secondary = SecondaryTeal,
    onSecondary = Color.White,
    secondaryContainer = SecondaryTealContainer,
    onSecondaryContainer = Color(0xFF115E59),
    tertiary = AccentAmber,
    onTertiary = Color.White,
    tertiaryContainer = AccentAmberContainer,
    onTertiaryContainer = Color(0xFF78350F),
    background = SlateBackgroundLight,
    surface = SlateSurfaceLight,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    error = StatusRose,
    errorContainer = StatusRoseContainer
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF93C5FD),
    onPrimary = Color(0xFF1E3A8A),
    primaryContainer = Color(0xFF1E40AF),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = Color(0xFF5EEAD4),
    onSecondary = Color(0xFF115E59),
    secondaryContainer = Color(0xFF0D9488),
    onSecondaryContainer = Color(0xFFCCFBF1),
    tertiary = Color(0xFFFCD34D),
    onTertiary = Color(0xFF78350F),
    tertiaryContainer = Color(0xFFD97706),
    onTertiaryContainer = Color(0xFFFEF3C7),
    background = SlateBackgroundDark,
    surface = SlateSurfaceDark,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    error = Color(0xFFFDA4AF),
    errorContainer = Color(0xFFE11D48)
)
