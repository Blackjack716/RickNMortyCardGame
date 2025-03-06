package com.rnm.ricknmortycards.ui.compose


sealed class PortalEvent {
    data object OnPortalClicked : PortalEvent()
}