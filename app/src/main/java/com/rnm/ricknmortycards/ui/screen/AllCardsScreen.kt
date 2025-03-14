package com.rnm.ricknmortycards.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.AnimatedBackgroundAura
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.compose.events.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.events.TopBarEvent
import com.rnm.ricknmortycards.ui.compose.sharedView.CardFrame
import com.rnm.ricknmortycards.ui.compose.sharedView.CharacterImage
import com.rnm.ricknmortycards.ui.compose.sharedView.CloseIcon
import com.rnm.ricknmortycards.ui.compose.sharedView.FavouriteIcon
import com.rnm.ricknmortycards.ui.compose.sharedView.FrameOfCard
import com.rnm.ricknmortycards.ui.compose.sharedView.ImageOfCharacter
import com.rnm.ricknmortycards.ui.compose.sharedView.NameOfCharacter
import com.rnm.ricknmortycards.ui.compose.sharedView.PortalButtons
import com.rnm.ricknmortycards.ui.compose.sharedView.ScaleableCardName
import com.rnm.ricknmortycards.ui.compose.sharedView.SortMenu
import com.rnm.ricknmortycards.ui.compose.uiState.AllCardsState
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState
import com.rnm.ricknmortycards.utils.MockCardsData
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true)
@Composable
fun AllCardsScreenPreview() {
    AllCardsScreen(
        onNavBardEvent = {},
        allCardsState = AllCardsState(MockCardsData.allCardsData, sortType = SortType.Id),
        onCardEvent = {},
        currencyState = null,
        onTopBarEvent = {},
        onPortalEvent = {}
    )
}

@Composable
fun AllCardsScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    allCardsState: AllCardsState,
    onCardEvent: (CardEvent) -> Unit,
    currencyState: Long?,
    onTopBarEvent: (TopBarEvent) -> Unit,
    onPortalEvent: (PortalEvent) -> Unit
) {

    val itemsSpacing = 8.dp
    val columnCount = 3

    val density = LocalDensity.current
    val parentWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val itemSpacingPx = with(density) { itemsSpacing.toPx() }
    val itemWidthPx = (parentWidthPx - (columnCount - 1) * itemSpacingPx) / columnCount
    val itemWidthDp = with(density) { itemWidthPx.toDp() }

    var openCardInfo by remember {
        mutableStateOf(false)
    }
    var cardInspected by remember {
        mutableStateOf<Card?>(null)
    }

    if (openCardInfo) {
        CardDialog(
            onDismissRequest = { openCardInfo = false },
            card = cardInspected,
            onPortalEvent = onPortalEvent,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CurrencyCounterBar(currencyState)
        SortMenu(onTopBarEvent)
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 62.dp)
                .align(Alignment.TopEnd),
            thickness = 2.dp,
            color = Color.LightGray
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 64.dp),
            columns = GridCells.Fixed(columnCount),
            horizontalArrangement = Arrangement.spacedBy(itemsSpacing),
            verticalArrangement = Arrangement.spacedBy(itemsSpacing),
        ) {

            allCardsState.cards.forEach {
                item {
                    Box(
                        modifier = Modifier
                            .height(itemWidthDp * 1.45f)
                            .background(color = Color.Transparent)
                            .clickable {
                                if (it.isOwned == true) {
                                    cardInspected = it
                                    openCardInfo = true
                                }
                            }
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

