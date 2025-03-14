package com.rnm.ricknmortycards.ui.compose.uiState

import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType

data class FavCardsState (
    val cards: List<Card>,
    val sortType: SortType
)