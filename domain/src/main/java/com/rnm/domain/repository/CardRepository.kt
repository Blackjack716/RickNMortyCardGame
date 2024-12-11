package com.rnm.domain.repository

interface CardRepository {
    suspend fun getAllCards()
    fun getCurrencyValue(): Float
    fun setCurrencyValue(value: Float)
    fun isDatabaseUpdated(): Boolean
}