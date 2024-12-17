package com.rnm.ricknmortycards.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavigationBottomBar(
    modifier: Modifier = Modifier,
    onEvent: (NavBarEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Red)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 50.dp)
                .fillMaxHeight()
                .background(color = Color.LightGray)
                .weight(0.3f)
                .clickable { onEvent(NavBarEvent.OnAllCardClicked) }
        ) {

        }
        Box(
            modifier = Modifier
                .widthIn(min = 50.dp)
                .fillMaxHeight()
                .background(color = Color.LightGray)
                .weight(0.3f)
                .clickable { onEvent(NavBarEvent.OnHomeClicked) }
        ) {

        }
        Box(
            modifier = Modifier
                .widthIn(min = 50.dp)
                .fillMaxHeight()
                .background(color = Color.LightGray)
                .weight(0.3f)
                .clickable { onEvent(NavBarEvent.OnFavCardClicked) }
        ) {

        }
    }
}