package com.rnm.ricknmortycards.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class AppColorScheme(
    val backgroundColor: Color,
    val secondaryBackgroundColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val gunTextColor: Color,
    val gunBackgroundColor: Color,
)

val LocalColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        backgroundColor = Color.Unspecified,
        secondaryBackgroundColor = Color.Unspecified,
        primaryTextColor = Color.Unspecified,
        secondaryTextColor = Color.Unspecified,
        gunTextColor = Color.Unspecified,
        gunBackgroundColor = Color.Unspecified,
    )
}

val lightColorScheme = AppColorScheme(
    backgroundColor = Color(0xFFF0F0F0),
    secondaryBackgroundColor = Color(0xFFF0F0F0),
    primaryTextColor = Color(0xFF000000),
    secondaryTextColor = Color(0xFF808080),
    gunTextColor = Color(0xFFD30E0E),
    gunBackgroundColor = Color(0xFF030E0E),
)
val darkColorScheme = AppColorScheme(
    backgroundColor = Color(0xFF030303),
    secondaryBackgroundColor = Color(0xFF1A1A1A),
    primaryTextColor = Color(0xFFFDFDFD),
    secondaryTextColor = Color(0xFFB6B6B6),
    gunTextColor = Color(0xFFD30E0E),
    gunBackgroundColor = Color(0xFF030E0E),
)