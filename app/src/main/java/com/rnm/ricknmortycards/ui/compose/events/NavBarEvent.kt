package com.rnm.ricknmortycards.ui.compose.events


sealed class NavBarEvent {
    data object OnAllCardClicked : NavBarEvent()
    data object OnFavCardClicked : NavBarEvent()
    data object OnHomeClicked : NavBarEvent()
}