package com.rnm.domain.repository

import com.rnm.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun getAllCards(): Flow<List<Card>>
    suspend fun getFavCards(): Flow<List<Card>>
    suspend fun getCurrencyValue(): Flow<Float>
    fun addCurrency(addedValue: Float)
    suspend fun isDatabaseUpdated(): Boolean
    suspend fun upgradeCard(cardId: Int)
    suspend fun sellCard(cardId: Int)
}