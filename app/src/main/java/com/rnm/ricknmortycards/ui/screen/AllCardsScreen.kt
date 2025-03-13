package com.rnm.ricknmortycards.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.compose.AnimatedBackgroundAura
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.utils.MockCardsData
import com.rnm.ricknmortycards.ui.compose.events.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.compose.sharedView.FavouriteIcon
import com.rnm.ricknmortycards.ui.compose.sharedView.FrameOfCard
import com.rnm.ricknmortycards.ui.compose.sharedView.ImageOfCharacter
import com.rnm.ricknmortycards.ui.compose.sharedView.NameOfCharacter

@Preview(showBackground = true)
@Composable
fun AllCardsScreenPreview() {
    AllCardsScreen(
        onNavBardEvent = {},
        allCardsState = MockCardsData.allCardsData,
        onCardEvent = {}
    )
}

@Composable
fun AllCardsScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    allCardsState: List<Card> = emptyList(),
    onCardEvent: (CardEvent) -> Unit
) {

    val itemsSpacing = 8.dp
    val columnCount = 3

    val density = LocalDensity.current
    val parentWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val itemSpacingPx = with(density) { itemsSpacing.toPx() }
    val itemWidthPx = (parentWidthPx - (columnCount - 1) * itemSpacingPx) / columnCount
    val itemWidthDp = with(density) { itemWidthPx.toDp() }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CurrencyCounterBar()
        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 42.dp),
            columns = GridCells.Fixed(columnCount),
            horizontalArrangement = Arrangement.spacedBy(itemsSpacing),
            verticalArrangement = Arrangement.spacedBy(itemsSpacing),
        ) {

            allCardsState.forEach {
                item {
                    Box(
                        modifier = Modifier
                            .height(itemWidthDp * 1.45f)
                            .background(color = Color.Transparent)
                    ) {

                        if (it.rarity != null) {
                            AnimatedBackgroundAura(
                                rarity = it.rarity
                            )
                        }

                        ImageOfCharacter(card = it)
                        FrameOfCard(card = it)
                        FavouriteIcon(card = it, onCardEvent = onCardEvent)
                        NameOfCharacter(card = it)

                    }
                }
            }
        }
        NavigationBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onEvent = onNavBardEvent
        )
    }
}

