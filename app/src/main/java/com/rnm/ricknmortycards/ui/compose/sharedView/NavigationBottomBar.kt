package com.rnm.ricknmortycards.ui.compose.sharedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.events.NavBarEvent
import com.rnm.ricknmortycards.ui.theme.AppTheme
import com.rnm.ricknmortycards.ui.theme.LocalColorScheme

@Preview
@Composable
fun NavigationBottomBarPreview() {
    NavigationBottomBar(
        modifier = Modifier,
        onEvent = {}
    )
}

@Composable
fun NavigationBottomBar(
    modifier: Modifier = Modifier,
    onEvent: (NavBarEvent) -> Unit
) {
    val colorScheme = AppTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 50.dp)
                .fillMaxHeight()
                .background(color = colorScheme.secondaryBackgroundColor)
                .weight(0.3f)
                .clickable { onEvent(NavBarEvent.OnAllCardClicked) }
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_navigation_collection),
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .widthIn(min = 50.dp)
                .fillMaxHeight()
                .background(color = colorScheme.secondaryBackgroundColor)
                .weight(0.3f)
                .clickable { onEvent(NavBarEvent.OnHomeClicked) }
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_navigation_home),
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .widthIn(min = 50.dp)
                .fillMaxHeight()
                .background(color = colorScheme.secondaryBackgroundColor)
                .weight(0.3f)
                .clickable { onEvent(NavBarEvent.OnFavCardClicked) }
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_navigation_favourites),
                contentDescription = null
            )
        }
    }
}