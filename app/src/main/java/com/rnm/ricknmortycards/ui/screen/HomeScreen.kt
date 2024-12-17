package com.rnm.ricknmortycards.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rnm.ricknmortycards.R
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
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CurrencyCounter()
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp),
                imageVector = ImageVector.vectorResource(R.drawable.home_background),
                contentDescription = null
            )
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PulsatingButton(
                    animationContent = {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFBFDE42))
                        )
                    },
                    content = {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF42B4CA))
                        ) {
                            Text(
                                text = stringResource(R.string.energy_level_button, 0, 10),
                                modifier = Modifier
                                    .align(Alignment.Center),
                                fontSize = 36.sp
                            )
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(150.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.LightGray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.energy_recharging_info),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                    Text("TIME")
                }
            }
            NavigationBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onEvent = onNavBardEvent
            )

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
private fun BoxScope.CurrencyCounter() {
    Row(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .size(30.dp),
            imageVector = ImageVector.vectorResource(R.drawable.icon_crystal),
            contentDescription = null
        )
        Text(
            text = "100000",
            modifier = Modifier
                .padding(end = 4.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

