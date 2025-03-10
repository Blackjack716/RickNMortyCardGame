package com.rnm.ricknmortycards.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar

@Composable
@Preview
fun FavCardsScreenPreview() {

}

@Composable
fun FavCardsScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    favCardsState: List<Card> = emptyList(),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CurrencyCounterBar()
        LazyColumn {

        }
        NavigationBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onEvent = onNavBardEvent
        )
    }
}