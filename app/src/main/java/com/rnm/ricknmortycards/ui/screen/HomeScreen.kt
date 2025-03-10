package com.rnm.ricknmortycards.ui.screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getDrawable
import coil3.compose.AsyncImage
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.compose.PortalEvent
import com.rnm.ricknmortycards.ui.compose.shimmerLoadingAnimation

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onNavBardEvent = {},
        onPortalEvent = {},
        cardState = null
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    //viewModel: MainViewModel = viewModel()
    cardState: Card?,
    onPortalEvent: (PortalEvent) -> Unit
) {
    var openPortal by remember {
        mutableStateOf(false)
    }

    if (openPortal) {
        CardDialog(
            onDismissRequest = { openPortal = false },
            cardState = cardState,
            onPortalEvent = onPortalEvent,
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CurrencyCounterBar()
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp),
                imageVector = ImageVector.vectorResource(R.drawable.home_background),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(),
                imageVector = ImageVector.vectorResource(R.drawable.portalgun_home),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 190.dp)
                    .width(80.dp),
                imageVector = ImageVector.vectorResource(R.drawable.glassfluidgreen_home),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 85.dp, start = 44.dp, end = 44.dp)
                    .fillMaxWidth()
                    .graphicsLayer(rotationX = 20f),
                text = "X USES LEFT",
                color = Color.Red
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 85.dp, end = 44.dp, start = 44.dp)
                    .fillMaxWidth()
                    .graphicsLayer(rotationX = 20f),
                text = "RECHARGING... XX:XX",
                color = Color.Red,
                textAlign = TextAlign.End
            )
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberDrawablePainter(
                        drawable = getDrawable(
                            LocalContext.current,
                            R.drawable.portal_home
                        )
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 120.dp)
                        .fillMaxWidth()
                        .clickable {
                            openPortal = true
                            onPortalEvent(PortalEvent.OnPortalClicked)
                        }
                )
            }
            NavigationBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onEvent = onNavBardEvent
            )
//            CardDialog(
//                onDismissRequest = { openPortal = false },
//                cardState = cardState,
//                onPortalEvent = onPortalEvent
//            )

        }
    }
}

@Composable
private fun PulsatingButton(
    pulseFraction: Float = 1.2f,
    alphaFraction: Float = 0.0f,
    animationContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition(label = "it")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        ), label = "its"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = alphaFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        ), label = "ita"
    )

    Box {
        Box(
            modifier = Modifier
                .scale(scale)
                .alpha(alpha)
        ) {
            animationContent()
        }
        content()
    }

}

@Composable
private fun CardDialog(
    onDismissRequest: () -> Unit = {},
    cardState: Card?,
    onPortalEvent: (PortalEvent) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.End)
                    .size(30.dp)
                    .border(width = 2.dp, shape = CircleShape, color = Color.LightGray)
                    .alpha(0.6f)
                    .clickable {
                        onDismissRequest()
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .size(width = 192.dp, height = 280.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Transparent)
            ) {
                var isLoading by remember {
                    mutableStateOf(true)
                }

                val backgroundColors: Pair<Color, Color> = when(cardState?.rarity) {
                    Card.RARITY_1 -> Color(0xFF00A6FF) to Color(0x6600A6FF)
                    Card.RARITY_2 -> Color(0xFF7A00FF) to Color(0x667A00FF)
                    Card.RARITY_3 -> Color(0xFFFF0A00) to Color(0x66FF0A00)
                    else -> Color(0xFF000000) to Color(0x66000000)
                }
                AnimatedCardBackground(
                    initialColor = backgroundColors.first,
                    targetColor = backgroundColors.second
                )


                AsyncImage(
                    model = cardState?.photoUrl,
                    contentDescription = cardState?.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight()
                        .align(Alignment.Center)
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
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.TopStart
                )

                Image(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(R.drawable.card_frame_black),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 15.dp, start = 16.dp)
                        .size(width = 32.dp, height = 16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = cardState?.id.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 8.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                var cardNameFontSize by remember {
                    mutableStateOf(24.sp)
                }
                var readyToDraw by remember {
                    mutableStateOf(false)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = cardState?.name ?: ("test test test test test test" +
                                "test test test test test test" +
                                "test test test test test test"),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 2.dp)
                            .align(Alignment.Center)
                            .drawWithContent {
                                if (readyToDraw) drawContent()
                            },
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF000000),
                        onTextLayout = { textLayoutResult ->
                            if (textLayoutResult.hasVisualOverflow) {
                                cardNameFontSize *= 0.9f
                            } else {
                                readyToDraw = true
                            }
                        },
                        fontSize = cardNameFontSize
                    )
                }
            }

            ElevatedButton(
                modifier = Modifier
                    .widthIn(min = 150.dp, max = 250.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = "OK"
                )
            }

            ElevatedButton(
                modifier = Modifier
                    .widthIn(min = 150.dp, max = 250.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    onPortalEvent(
                        PortalEvent.OnPortalSellButtonClicked(
                            cardState?.id ?: 0,
                            cardState?.sellValue ?: 0f
                        )
                    )
                }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = "SELL"
                )
            }
        }

    }
}

@Composable
fun BoxScope.AnimatedCardBackground(initialColor: Color, targetColor: Color) {
    val animatedColor by rememberInfiniteTransition().animateColor(
        initialValue = initialColor,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(6000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
            .blur(radius = 30.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = animatedColor, shape = RoundedCornerShape(5.dp))
        ) {}
    }
}

