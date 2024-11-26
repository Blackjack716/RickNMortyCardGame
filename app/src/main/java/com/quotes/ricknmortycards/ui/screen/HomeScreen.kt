package com.quotes.ricknmortycards.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.quotes.ricknmortycards.ui.MainViewModel
import com.quotes.ricknmortycards.ui.theme.RickNMortyCardsTheme

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel()
) {
    RickNMortyCardsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .drawBehind {
                        //ikona
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PulsatingButton(
                    animationContent = {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                    },
                    content = {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        ) {

                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(150.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Red)
                ) {
                    Text("info")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Red),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(min = 50.dp)
                        .fillMaxHeight()
                        .background(color = Color.LightGray)
                        .weight(0.3f)
                ){

                }
                Box(
                    modifier = Modifier
                        .widthIn(min = 50.dp)
                        .fillMaxHeight()
                        .background(color = Color.LightGray)
                        .weight(0.3f)
                ){

                }
                Box(
                    modifier = Modifier
                        .widthIn(min = 50.dp)
                        .fillMaxHeight()
                        .background(color = Color.LightGray)
                        .weight(0.3f)
                ){

                }
            }
        }
    }
}

@Composable
fun PulsatingButton(
    pulseFraction: Float = 1.2f,
    alphaFraction: Float = 0.0f,
    animationContent: @Composable () -> Unit,
    content: @Composable () -> Unit) {

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
        Box(modifier = Modifier.scale(scale).alpha(alpha)) {
            animationContent()
        }
        content()
    }

}