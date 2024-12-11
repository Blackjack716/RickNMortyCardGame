package com.rnm.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : CardRepository {

    override suspend fun getAllCards() {
        TODO("Not yet implemented")
    }

    override fun getCurrencyValue(): Float {
        return sharedPreferences.getFloat(CURRENCY_VALUE, 0f)
    }

    override fun setCurrencyValue(value: Float) {
        sharedPreferences.edit {
            putFloat(CURRENCY_VALUE, value)
        }
    }

    override fun isDatabaseUpdated(): Boolean {
        return sharedPreferences.getBoolean(IS_DATABASE_UP_TO_DATE, false)
    }

    companion object {
        const val CURRENCY_VALUE = "currencyValue"
        const val IS_DATABASE_UP_TO_DATE = "isDatabaseUpToDate"
    }

}