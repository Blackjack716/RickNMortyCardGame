package com.rnm.ricknmortycards.ui.compose.sharedView

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.theme.LocalColorScheme
import com.rnm.ricknmortycards.utils.BottomTriangleShape
import com.rnm.ricknmortycards.utils.LeftTriangleShape
import com.rnm.ricknmortycards.utils.RightTriangleShape
import com.rnm.ricknmortycards.utils.TopTriangleShape

@Preview
@Composable
fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .size(width = 200.dp, height = 280.dp)
        ) {
            AnimatedBackgroundAura(rarity = 1)
        }

    }
}

@Composable
fun BoxScope.AnimatedBackgroundAura(rarity: Int?, colorStartingRange: Float = 0.05f, animationTime: Int = 6000) {

    val backgroundColors: Pair<Color, Color> = when(rarity) {
        Card.RARITY_1 -> Color(0xAA00A6FF) to Color(0x4400A6FF)
        Card.RARITY_2 -> Color(0xAA7A00FF) to Color(0x447A00FF)
        Card.RARITY_3 -> Color(0xAAFF0A00) to Color(0x44FF0A00)
        else -> Color(0xAA000000) to Color(0x44000000)
    }

    val initialColor = backgroundColors.first
    val targetColor = backgroundColors.second

    val animatedColor by rememberInfiniteTransition().animateColor(
        initialValue = initialColor,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(animationTime),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorAnimation"
    )

    val colorStops = arrayOf(
        0.0f to Color.Transparent,
        colorStartingRange to animatedColor,
        1f - colorStartingRange to animatedColor,
        1f to Color.Transparent
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colorStops = colorStops
                ),
                shape = LeftTriangleShape()
            )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colorStops = colorStops
                ),
                shape = RightTriangleShape()
            )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = colorStops
                ),
                shape = TopTriangleShape()
            )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = colorStops
                ),
                shape = BottomTriangleShape()
            )
    )
}