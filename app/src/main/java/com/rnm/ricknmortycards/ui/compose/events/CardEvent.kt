package com.rnm.ricknmortycards.ui.compose.events

import com.rnm.domain.model.Card


sealed class CardEvent {
    data class OnCardClicked(val card: Card)
    data class OnFavClicked(val cardId: Int) : CardEvent()
    data class OnSellClicked(val cardId: Int, val price: Int) : CardEvent()
}