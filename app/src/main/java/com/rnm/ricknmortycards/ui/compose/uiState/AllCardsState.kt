package com.rnm.ricknmortycards.ui.compose.uiState

import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType

data class AllCardsState (
    val cards: List<Card>,
    val sortType: SortType
)