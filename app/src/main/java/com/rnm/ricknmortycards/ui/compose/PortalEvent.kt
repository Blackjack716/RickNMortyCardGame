package com.rnm.ricknmortycards.ui.compose


sealed class PortalEvent {
    data object OnPortalClicked : PortalEvent()
    data object OnPortalDismissed : PortalEvent()
    data class OnPortalSellButtonClicked(val cardId: Int, val price: Float) : PortalEvent()
}