package com.rnm.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "photo_url") val photoUrl: String?,
    @ColumnInfo(name = "isFav") val isFavourite: Boolean?,
    @ColumnInfo(name = "isOwned") val isOwned: Boolean?,
    @ColumnInfo(name = "rarity") val rarity: Int?,
    @ColumnInfo(name = "upgradeCost") val upgradeCost: Float?,
    @ColumnInfo(name = "sellValue") val sellValue: Float?
) {
    companion object {
        const val RARITY_1 = 1
        const val RARITY_2 = 2
        const val RARITY_3 = 3
        const val UPGRADE_COST_1 = 100f
        const val UPGRADE_COST_2 = 100f
        const val UPGRADE_COST_3 = 100f
        const val SELL_VALUE_1 = 100f
        const val SELL_VALUE_2 = 100f
        const val SELL_VALUE_3 = 100f
    }
}