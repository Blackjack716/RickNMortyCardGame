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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getDrawable
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onNavBardEvent = {},
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    //viewModel: MainViewModel = viewModel()
) {
    var openPortal by remember {
        mutableStateOf(false)
    }

    if (openPortal) {
        CardDialog(
            onDismissRequest = { openPortal = false }
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
                    .fillMaxWidth()
                ,
                imageVector = ImageVector.vectorResource(R.drawable.portalgun_home),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 190.dp)
                    .width(80.dp)
                ,
                imageVector = ImageVector.vectorResource(R.drawable.glassfluidgreen_home),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 85.dp, start = 44.dp, end = 44.dp)
                    .fillMaxWidth()
                    .graphicsLayer(rotationX = 20f)
                ,
                text = "X USES LEFT",
                color = Color.Red
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 85.dp, end = 44.dp, start = 44.dp)
                    .fillMaxWidth()
                    .graphicsLayer(rotationX = 20f)
                ,
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
                        .clickable { openPortal = true }
                )
            }
            NavigationBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onEvent = onNavBardEvent
            )
            CardDialog()

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
fun CardDialog(
    onDismissRequest: () -> Unit = {}
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
                    .alpha(0.6f),
                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                contentDescription = null
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
                    .background(color = Color.Red)
            ) {

            }

            ElevatedButton(
                modifier = Modifier
                    .widthIn(min = 150.dp, max = 250.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {}
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
                onClick = {}
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = "SELL"
                )
            }
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

    }
}

