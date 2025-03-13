package com.rnm.ricknmortycards.ui.compose.events


sealed class CardEvent {
    data class OnFavClicked(val cardId: Int) : CardEvent()
    data class OnSellClicked(val cardId: Int, val price: Int) : CardEvent()
}