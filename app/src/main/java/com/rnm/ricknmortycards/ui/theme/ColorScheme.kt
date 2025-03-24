package com.rnm.ricknmortycards.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val backgroundColor: Color,
    val secondaryBackgroundColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val gunTextColor: Color,
    val gunBackgroundColor: Color,
    val positiveButtonColor: Color,
    val negativeButtonColor: Color,
    val buttonColor: Color
)

val LocalColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        backgroundColor = Color(0xFFF0F0F0),
        secondaryBackgroundColor = Color(0xFF7E7E7E),
        primaryTextColor = Color(0xFF000000),
        secondaryTextColor = Color(0xFF808080),
        gunTextColor = Color(0xFFD30E0E),
        gunBackgroundColor = Color(0xFF030E0E),
        positiveButtonColor = Color(0xFF77D756),
        negativeButtonColor = Color(0xFFC96060),
        buttonColor = Color(0xFF7E7E7E)
    )
}

val lightColorScheme = AppColorScheme(
    backgroundColor = Color(0xFFF0F0F0),
    secondaryBackgroundColor = Color(0xFF7E7E7E),
    primaryTextColor = Color(0xFF000000),
    secondaryTextColor = Color(0xFF808080),
    gunTextColor = Color(0xFFD30E0E),
    gunBackgroundColor = Color(0xFF030E0E),
    positiveButtonColor = Color(0xFF77D756),
    negativeButtonColor = Color(0xFFC96060),
    buttonColor = Color(0xFF7E7E7E)

)
val darkColorScheme = AppColorScheme(
    backgroundColor = Color(0xFF030303),
    secondaryBackgroundColor = Color(0xFF3B3B3B),
    primaryTextColor = Color(0xFFFDFDFD),
    secondaryTextColor = Color(0xFFB6B6B6),
    gunTextColor = Color(0xFFD30E0E),
    gunBackgroundColor = Color(0xFF030E0E),
    positiveButtonColor = Color(0xFF3B9D1B),
    negativeButtonColor = Color(0xFF831C1C),
    buttonColor = Color(0xFF3B3B3B)
)

