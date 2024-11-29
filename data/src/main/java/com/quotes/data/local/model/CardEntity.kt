package com.quotes.data.local.model

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
)