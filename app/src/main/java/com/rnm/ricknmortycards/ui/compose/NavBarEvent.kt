package com.rnm.ricknmortycards.ui.compose


sealed class NavBarEvent {
    data object OnAllCardClicked : NavBarEvent()
    data object OnFavCardClicked : NavBarEvent()
    data object OnHomeClicked : NavBarEvent()
}