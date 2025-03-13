package com.rnm.ricknmortycards.ui.screen

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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.AnimatedBackgroundAura
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.ui.compose.events.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.shimmerLoadingAnimation
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onNavBardEvent = {},
        onPortalEvent = {},
        state = null
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    state: HomeState?,
    onPortalEvent: (PortalEvent) -> Unit
) {
    var openPortal by remember {
        mutableStateOf(false)
    }

    if (openPortal) {
        CardDialog(
            onDismissRequest = { openPortal = false },
            state = state,
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

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = R.drawable.portal_home,
                    contentDescription = null,
                    modifier = modifier
                        .padding(bottom = 120.dp)
                        .fillMaxWidth(0.7f)
                        .clickable {
                            openPortal = true
                            onPortalEvent(PortalEvent.OnPortalClicked)
                        }
                )
            }

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
                text = "${state?.energyLevelState ?: 0} USES LEFT",
                color = Color.Red
            )

            var rechargingTime by remember {
                mutableStateOf("RECHARGING... " + convertLongToTimeLeft(state?.energyRechargeTimeState))
            }

            LaunchedEffect(key1 = state?.energyLevelState) {
                if ((state?.energyLevelState ?: 10) >= 10) {
                    rechargingTime = "ENERGY FULL"
                } else {
                    while (true) {
                        delay(1000)
                        rechargingTime = "RECHARGING... " + convertLongToTimeLeft(state?.energyRechargeTimeState)
                    }
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 85.dp, end = 44.dp, start = 44.dp)
                    .fillMaxWidth()
                    .graphicsLayer(rotationX = 20f),
                text = rechargingTime,
                color = Color.Red,
                textAlign = TextAlign.End
            )

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
    state: HomeState?,
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

                AnimatedBackgroundAura(
                    rarity = state?.cardState?.rarity,
                    animationTime = 2000
                )

                AsyncImage(
                    model = state?.cardState?.photoUrl,
                    contentDescription = state?.cardState?.name,
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

                val imageResource = when (state?.cardState?.rarity) {
                    Card.RARITY_1 -> ImageVector.vectorResource(R.drawable.card_frame_blue)
                    Card.RARITY_2 -> ImageVector.vectorResource(R.drawable.card_frame_purple)
                    Card.RARITY_3 -> ImageVector.vectorResource(R.drawable.card_frame_red)
                    else -> ImageVector.vectorResource(R.drawable.card_frame_black)
                }

                Image(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    imageVector = imageResource,
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
                        text = state?.cardState?.id.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 8.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                var cardNameFontSize by remember {
                    mutableStateOf(22.sp)
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
                        text = state?.cardState?.name ?: ("test test test test test test" +
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
                            state?.cardState?.id ?: 0,
                            state?.cardState?.sellValue ?: 0f
                        )
                    )
                },
                colors = ButtonColors(
                    containerColor = Color(0xFFC96060),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                )
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

private fun convertLongToTimeLeft(time: Long?): String {
    if (time == null) return ""

    val currentTime = System.currentTimeMillis()
    val date = Date(time - currentTime)
    val format = SimpleDateFormat("mm:ss", Locale.getDefault())
    return format.format(date)
}

