package com.rnm.ricknmortycards.ui.compose.uiState

import com.rnm.domain.model.Card

data class HomeState (
    val cardState: Card?,
    val energyLevelState: Int?,
    val energyRechargeTimeState: Long?,
)