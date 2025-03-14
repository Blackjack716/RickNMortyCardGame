package com.rnm.domain.model

data class Card(
    val id: Int?,
    val name: String?,
    val photoUrl: String?,
    val isFavourite: Boolean?,
    val isOwned: Boolean?,
    val rarity: Int?,
    val upgradeCost: Float?,
    val sellValue: Float?,
    val isDuplicate: Boolean? = false
) {
    companion object {
        const val RARITY_1 = 1
        const val RARITY_2 = 2
        const val RARITY_3 = 3
        const val UPGRADE_COST_1 = 100f
        const val UPGRADE_COST_2 = 200f
        const val UPGRADE_COST_3 = 500f
        const val SELL_VALUE_1 = 10f
        const val SELL_VALUE_2 = 50f
        const val SELL_VALUE_3 = 200f
    }
}


