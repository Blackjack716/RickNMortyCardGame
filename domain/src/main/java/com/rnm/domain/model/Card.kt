package com.rnm.domain.model

data class Card(
    val name: String?,
    val photoUrl: String?,
    val isFavourite: Boolean?,
    val isOwned: Boolean?,
    val rarity: Int?,
    val upgradeCost: Float?,
    val sellValue: Float?
)
