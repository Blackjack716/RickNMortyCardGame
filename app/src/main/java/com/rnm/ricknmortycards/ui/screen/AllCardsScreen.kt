package com.rnm.ricknmortycards.ui.screen

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.utils.MockCardsData
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.compose.shimmerLoadingAnimation
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun AllCardsScreenPreview() {
    AllCardsScreen(
        onNavBardEvent = {},
        allCardsState = MockCardsData.allCardsData
    )
}

@Composable
fun AllCardsScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    allCardsState: List<Card> = emptyList(),
) {
    val bottomTextColorStops = arrayOf(
        0.0f to Color.Black,
        0.5f to Color.Black,
        1f to Color.Transparent
    )

    val bottomTextGradient = Brush.verticalGradient(
        colorStops = bottomTextColorStops,
        startY = Float.POSITIVE_INFINITY,
        endY = 0.0f
    )

    val itemsSpacing = 8.dp
    val columnCount = 3

    val density = LocalDensity.current
    val parentWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val itemSpacingPx = with(density) { itemsSpacing.toPx() }
    val itemWidthPx = (parentWidthPx - (columnCount - 1) * itemSpacingPx) / columnCount
    val itemWidthDp = with(density) { itemWidthPx.toDp() }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CurrencyCounterBar()
        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 42.dp),
            columns = GridCells.Fixed(columnCount),
            horizontalArrangement = Arrangement.spacedBy(itemsSpacing),
            verticalArrangement = Arrangement.spacedBy(itemsSpacing),
        ) {

            allCardsState.forEach {
                item {
                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .background(color = Color.Cyan)
                    ) {
                        var isLoading by remember {
                            mutableStateOf(true)
                        }


                        AsyncImage(
                            model = it.photoUrl,
                            contentDescription = it.name,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .fillMaxSize()
                                .then(
                                    if (isLoading) {
                                        Modifier.shimmerLoadingAnimation(
                                            isLoadingCompleted = false,
                                            isLightModeActive = !isSystemInDarkTheme()
                                        )
                                    } else {
                                        Modifier
                                    }
                                ),
                            onLoading = {
                                isLoading = true
                            },
                            onSuccess = {
                                isLoading = false
                            },
                            onError = {
                                isLoading = false
                            },
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.TopStart
                        )

                        Image(
                            modifier = Modifier
                                .padding(vertical = 0.dp)
                                .fillMaxSize(),
                            imageVector = ImageVector.vectorResource(R.drawable.photo_frame_black),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 2.dp, start = 4.dp)
                                .size(30.dp)
                                .alpha(0.5f)
                                .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        ) {
                            Text("1")
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(30.dp)
                                .alpha(0.5f)
                                .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                        ) {
                            if (it.isFavourite == true) {
                                Image(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(24.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_star_favourite),
                                    contentDescription = null
                                )
                            } else {
                                Image(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(24.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_star_no_favourite),
                                    contentDescription = null
                                )
                            }

                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .alpha(0.4f)
                                .background(brush = bottomTextGradient, shape = RoundedCornerShape(4.dp))
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = "${it.name}",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                ,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
        NavigationBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onEvent = onNavBardEvent
        )
    }
}