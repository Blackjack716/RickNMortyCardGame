package com.rnm.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rnm.domain.model.Card.Companion.RARITY_1
import com.rnm.domain.model.Card.Companion.RARITY_2
import com.rnm.domain.model.Card.Companion.RARITY_3
import com.rnm.domain.model.Card.Companion.SELL_VALUE_1
import com.rnm.domain.model.Card.Companion.SELL_VALUE_2
import com.rnm.domain.model.Card.Companion.SELL_VALUE_3
import com.rnm.domain.model.Card.Companion.UPGRADE_COST_1
import com.rnm.domain.model.Card.Companion.UPGRADE_COST_2
import com.rnm.domain.model.Card.Companion.UPGRADE_COST_3

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
)

fun CardEntity.getCardUpgradeCost(cardRarity: Int? = null): Float {
    return if (cardRarity != null) {
        when (cardRarity) {
            1 -> UPGRADE_COST_2
            2 -> UPGRADE_COST_3
            else -> 0f
        }
    } else {
        when (rarity) {
            null -> UPGRADE_COST_1
            1 -> UPGRADE_COST_2
            2 -> UPGRADE_COST_3
            else -> 0f
        }
    }

}

fun CardEntity.getCardSellCost(cardRarity: Int? = null): Float {
    return if (cardRarity != null) {
        when (cardRarity) {
            1 -> SELL_VALUE_1
            2 -> SELL_VALUE_2
            3 -> SELL_VALUE_3
            else -> 0f
        }
    } else {
        when (rarity) {
            1 -> SELL_VALUE_1
            2 -> SELL_VALUE_2
            3 -> SELL_VALUE_3
            else -> 0f
        }
    }
}

fun CardEntity.getBetterCardRarity(): Int {
    return when(rarity) {
        1 -> RARITY_2
        2 -> RARITY_3
        else -> RARITY_1
    }
}