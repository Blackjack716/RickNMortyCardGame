package com.rnm.domain.repository

interface CardRepository {
    suspend fun getAllCards()
    fun getCurrencyValue(): Float
    fun editCurrencyValue(value: Float)
    fun isDatabaseUpdated(): Boolean
}