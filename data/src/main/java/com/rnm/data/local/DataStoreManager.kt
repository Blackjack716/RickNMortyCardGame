package com.rnm.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rnm.domain.model.SortType
import com.rnm.domain.model.toInt
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("RNMData")
    private val dataStore = context.datastore

    suspend fun setCardsDatabaseUpdated() {
        dataStore.edit {
            it[IS_CARDS_UPDATED_KEY] = true
        }
    }

    fun getIsDatabaseUpdated(): Flow<Boolean> {
        return try {
            dataStore.data.map {
                it[IS_CARDS_UPDATED_KEY] ?: false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            flowOf(false)
        }
    }

    fun getCurrencyValueSync(): Float {
        return runBlocking {
            dataStore.data.map {
                it[CURRENCY_VALUE_KEY]
            }.firstOrNull() ?: 0f
        }
    }

    fun getCurrencyValue(): Flow<Float> {
        return dataStore.data.map {
            it[CURRENCY_VALUE_KEY] ?: 0f
        }
    }

    suspend fun addCurrencyValue(addedValue: Float) {
        dataStore.edit {
            val value = it[CURRENCY_VALUE_KEY] ?: 0f
            it[CURRENCY_VALUE_KEY] = value + addedValue
        }
    }

    suspend fun setSortType(sortType: SortType) {
        dataStore.edit {
            it[SORT_TYPE_KEY] = sortType.toInt()
        }
    }

    fun getSortType(): Flow<Int> {
        return dataStore.data.map {
            it[SORT_TYPE_KEY] ?: 0
        }
    }

    fun getEnergyRechargeTime(): Flow<Long> {
        return dataStore.data.map {
            it[ENERGY_RECHARGE_TIME_KEY] ?: 0
        }
    }

    suspend fun setEnergyRechargeTime(time: Long) {
        dataStore.edit {
            it[ENERGY_RECHARGE_TIME_KEY] = time
        }
    }

    companion object {
        val IS_CARDS_UPDATED_KEY = booleanPreferencesKey("isCardsUpdated")
        val CURRENCY_VALUE_KEY = floatPreferencesKey("currencyValueKey")
        val SORT_TYPE_KEY = intPreferencesKey("sortTypeKey")
        val ENERGY_RECHARGE_TIME_KEY = longPreferencesKey("energyRechargeTimeKey")

    }
}