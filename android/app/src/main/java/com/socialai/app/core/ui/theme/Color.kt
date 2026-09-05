package com.socialai.app.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Primary SocialAI Deep Forest Green Brand Colors
val PrimaryGreen = Color(0xFF00382B)
val PrimaryGreenLight = Color(0xFF0A523E)
val PrimaryGreenContainer = Color(0xFFE2F0EC)

val SecondaryEmerald = Color(0xFF0D7A5F)
val SecondaryEmeraldContainer = Color(0xFFD3EBE4)

val AccentOrange = Color(0xFFE58000)
val AccentOrangeContainer = Color(0xFFFFF3E0)

val StatusGreen = Color(0xFF059669)
val StatusGreenContainer = Color(0xFFD1FAE5)

val StatusRose = Color(0xFFE11D48)
val StatusRoseContainer = Color(0xFFFFE4E6)

val SlateBackgroundLight = Color(0xFFF4F6F5)
val SlateSurfaceLight = Color(0xFFFFFFFF)

val SlateBackgroundDark = Color(0xFF0F172A)
val SlateSurfaceDark = Color(0xFF1E293B)

val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = PrimaryGreenContainer,
    onPrimaryContainer = Color(0xFF00221A),
    secondary = SecondaryEmerald,
    onSecondary = Color.White,
    secondaryContainer = SecondaryEmeraldContainer,
    onSecondaryContainer = Color(0xFF002D22),
    tertiary = AccentOrange,
    onTertiary = Color.White,
    tertiaryContainer = AccentOrangeContainer,
    onTertiaryContainer = Color(0xFF522E00),
    background = SlateBackgroundLight,
    surface = SlateSurfaceLight,
    surfaceVariant = Color(0xFFE8ECE9),
    onSurfaceVariant = Color(0xFF424945),
    error = StatusRose,
    errorContainer = StatusRoseContainer
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF63DCB4),
    onPrimary = Color(0xFF00382B),
    primaryContainer = PrimaryGreen,
    onPrimaryContainer = Color(0xFFE2F0EC),
    secondary = Color(0xFF7BD8BE),
    onSecondary = Color(0xFF00382B),
    secondaryContainer = SecondaryEmerald,
    onSecondaryContainer = Color(0xFFD3EBE4),
    tertiary = Color(0xFFFFB961),
    onTertiary = Color(0xFF452400),
    tertiaryContainer = AccentOrange,
    onTertiaryContainer = Color(0xFFFFF3E0),
    background = SlateBackgroundDark,
    surface = SlateSurfaceDark,
    surfaceVariant = Color(0xFF2C3531),
    onSurfaceVariant = Color(0xFFC1C8C4),
    error = Color(0xFFFDA4AF),
    errorContainer = Color(0xFFE11D48)
)

