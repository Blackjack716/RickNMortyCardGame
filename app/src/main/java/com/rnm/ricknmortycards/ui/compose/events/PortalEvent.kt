package com.rnm.ricknmortycards.ui.compose.events


sealed class PortalEvent {
    data object OnPortalClicked : PortalEvent()
    data object OnPortalDismissed : PortalEvent()
    data class OnPortalSellButtonClicked(val cardId: Int, val price: Float) : PortalEvent()
    data class OnPortalUpgradeButtonClicked(val cardId: Int, val price: Float) : PortalEvent()
}