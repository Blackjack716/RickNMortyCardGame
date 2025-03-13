package com.rnm.ricknmortycards.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

abstract class CustomShape : Shape {
    companion object {
        const val DEFAULT_CORNER_RADIUS = 20f
    }
}

class LeftTriangleShape : CustomShape() {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width / 2f, size.height / 2f)
            lineTo(0f, size.height)
            close()
        }
        val path2 = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width / 2f,
                    bottom = size.height,
                    cornerRadius = CornerRadius(DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS)
                )
            )
        }
        val path3 = Path().apply {
            op(path, path2, PathOperation.Intersect)
        }
        return Outline.Generic(path3)
    }
}

class RightTriangleShape : CustomShape() {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width, 0f)
            lineTo(size.width / 2f, size.height / 2f)
            lineTo(size.width, size.height)
            close()
        }
        val path2 = Path().apply {
            addRoundRect(
                RoundRect(
                    left = size.width / 2f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = CornerRadius(DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS)
                )
            )
        }
        val path3 = Path().apply {
            op(path, path2, PathOperation.Intersect)
        }
        return Outline.Generic(path3)
    }
}

class TopTriangleShape : CustomShape() {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width / 2f, size.height / 2f)
            lineTo(size.width, 0f)
            close()
        }
        val path2 = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height / 2f,
                    cornerRadius = CornerRadius(DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS)
                )
            )
        }
        val path3 = Path().apply {
            op(path, path2, PathOperation.Intersect)
        }
        return Outline.Generic(path3)
    }
}

class BottomTriangleShape : CustomShape() {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, size.height)
            lineTo(size.width / 2f, size.height / 2f)
            lineTo(size.width, size.height)
            close()
        }
        val path2 = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = size.height / 2f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = CornerRadius(DEFAULT_CORNER_RADIUS, DEFAULT_CORNER_RADIUS)
                )
            )
        }
        val path3 = Path().apply {
            op(path, path2, PathOperation.Intersect)
        }
        return Outline.Generic(path3)
    }
}

@Preview
@Composable
fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RightTriangleShape())
                .background(Color.Red)
        )
    }
}