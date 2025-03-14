package com.rnm.ricknmortycards.ui.compose.events

import com.rnm.domain.model.SortType


sealed class TopBarEvent {
    data class OnSortClicked(val sortType: SortType) : TopBarEvent()
}