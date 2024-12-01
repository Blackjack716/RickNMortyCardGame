package com.rnm.ricknmortycards.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.MainViewModel
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.theme.RickNMortyCardsTheme

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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

                            }
                        }
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .width(150.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.LightGray)
                    ) {
                        Text(text = "next energy unit in TIME", modifier = Modifier.padding(horizontal = 8.dp))
                    }
                }
                NavigationBottomBar(modifier = Modifier.align(Alignment.BottomCenter))

            }
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

