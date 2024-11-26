package com.quotes.ricknmortycards.ui.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
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
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ) {  }
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