package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NeonJade,
    onPrimary = Color(0xFF003825),
    primaryContainer = JadeDark,
    onPrimaryContainer = Color(0xFF90F8CD),
    secondary = NeonGold,
    onSecondary = Color(0xFF3F2E00),
    secondaryContainer = Color(0xFF5B4300),
    onSecondaryContainer = Color(0xFFFFDF9E),
    tertiary = Color(0xFFFFB3B8),
    onTertiary = Color(0xFF561D24),
    background = NightDarkBg,
    onBackground = Color(0xFFE1E3DF),
    surface = NightSurface,
    onSurface = Color(0xFFE1E3DF),
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = Color(0xFFC0C9C3),
    outline = NightBorder,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = JadeGreen,
    onPrimary = Color.White,
    primaryContainer = JadeContainer,
    onPrimaryContainer = OnJadeContainer,
    secondary = ShwedagonGold,
    onSecondary = Color.Black,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = OnGoldContainer,
    tertiary = BurmeseMaroon,
    onTertiary = Color.White,
    tertiaryContainer = MaroonContainer,
    onTertiaryContainer = OnMaroonContainer,
    background = WarmSand,
    onBackground = Color(0xFF191C1B),
    surface = Color.White,
    onSurface = Color(0xFF191C1B),
    surfaceVariant = Color(0xFFEDEFEA),
    onSurfaceVariant = Color(0xFF404944),
    outline = Color(0xFF707973),
    error = EmergencyRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep Myanmar branded palette prominent
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
