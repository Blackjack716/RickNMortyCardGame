package com.rnm.ricknmortycards.ui.compose

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rnm.domain.model.Card

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
                //.border(1.dp, color = Color.Black)
        ) {
            AnimatedCardBackground(rarity = 1)
        }

    }
}

@Composable
fun BoxScope.AnimatedCardBackground(rarity: Int?, radius: Dp = 30.dp, backgroundColor: Color = Color.White) {

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
            animation = tween(6000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorAnimation"
    )

    val horizontalColorStops = arrayOf(
        0.0f to Color.Transparent,
        0.05f to initialColor,
        0.95f to initialColor,
        1f to Color.Transparent
    )
    val verticalColorStops = arrayOf(
        0.0f to Color.Transparent,
        0.05f to Color.Red,
        0.95f to Color.Red,
        1f to Color.Transparent
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
            .background(
                brush = Brush.horizontalGradient(
                    colorStops = horizontalColorStops
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .drawWithCache {
                val path = Path()
                path.moveTo(0f, 0f)
                path.lineTo(size.width / 2f, size.height / 2f)
                path.lineTo(size.width, 0f)
                path.close()
                val path2 = Path()
                path2.moveTo(0f, size.height)
                path2.lineTo(size.width / 2f, size.height / 2f)
                path2.lineTo(size.width, size.height)
                path2.close()
                onDrawWithContent {
                    drawContent()
                    drawPath(path, backgroundColor)
                    drawPath(path2, backgroundColor)
                }
            }
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = verticalColorStops
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .drawWithCache {
                val path = Path()
                path.moveTo(0f, 0f)
                path.lineTo(size.width / 2f, size.height / 2f)
                path.lineTo(0f, size.height)
                path.close()
                val path2 = Path()
                path2.moveTo(size.width, 0f)
                path2.lineTo(size.width / 2f, size.height / 2f)
                path2.lineTo(size.width, size.height)
                path2.close()
                onDrawWithContent {
                    drawContent()
                    drawPath(path, backgroundColor)
                    drawPath(path2, backgroundColor)
                }
            }
    )
}