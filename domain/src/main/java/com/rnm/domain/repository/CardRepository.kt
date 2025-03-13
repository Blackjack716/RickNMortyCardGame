package com.rnm.domain.repository

import com.rnm.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun getAllCards(): Flow<List<Card>>
    suspend fun getFavCards(): Flow<List<Card>>
    fun setCardAsFav(cardId: Int)
    suspend fun getCurrencyValue(): Flow<Float>
    fun addCurrency(addedValue: Float)
    suspend fun isDatabaseUpdated(): Boolean
    suspend fun upgradeCard(cardId: Int, rarity: Int? = null)
    fun sellCard(cardId: Int)
    suspend fun getCardCount(): Int
    suspend fun getCard(cardId: Int): Flow<Card>
    suspend fun getEnergyLevel(): Flow<Int>
    fun setEnergyLevel(energyLevel: Int)
    suspend fun getEnergyRechargeTime(): Flow<Long>
}