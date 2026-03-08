package com.infiniti.clock.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Custom dark green accent color mapping to Infiniti branding.
 */
val InfinitiGreen = Color(0xFF003D33)

/**
 * Custom gold accent color mapping to Infiniti branding.
 */
val InfinitiGold = Color(0xFFB0985A)

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Color.Gray,
    tertiary = InfinitiGreen,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Black,
    secondary = Color.DarkGray,
    tertiary = InfinitiGreen,
    background = Color(0xFFF0F0F0),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

/**
 * The core theme applied to the Infiniti Clock app.
 * Automatically handles the transition between light and dark mode, * utilizing a simplified pure black and white palette for night driving.
 *
 * @param darkTheme Automatically defaults to [isSystemInDarkTheme] representing the car's *                  headlight/ambient light sensor status.
 * @param content The composable content to render underneath the applied Material 3 color scheme.
 */
@Composable
fun InfinitiClockTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
