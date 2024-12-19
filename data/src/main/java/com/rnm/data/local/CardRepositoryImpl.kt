package com.rnm.data.local

import com.rnm.data.di.DataStoreManager
import com.rnm.data.local.dao.CardDao
import com.rnm.data.local.model.getBetterCardRarity
import com.rnm.data.local.model.getCardSellCost
import com.rnm.data.local.model.getCardUpgradeCost
import com.rnm.data.local.model.toCard
import com.rnm.domain.model.Card
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val cardDao: CardDao
) : CardRepository {

    override suspend fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAllCards().map { it.toCard() }
    }

    override suspend fun getFavCards(): Flow<List<Card>> {
        return cardDao.getFavouriteCards().map { it.toCard() }
    }

    override suspend fun getCurrencyValue(): Flow<Float> {
        return dataStoreManager.getCurrencyValue()
    }

    override fun addCurrency(addedValue: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.addCurrencyValue(addedValue)
        }
    }

    override suspend fun isDatabaseUpdated(): Boolean {
        return dataStoreManager.getIsDatabaseUpdated()
    }

    override suspend fun upgradeCard(cardId: Int) {
        val card = cardDao.getCard(cardId)
        card.collect {
            cardDao.updateCard(
                it.copy(
                    isOwned = true,
                    rarity = it.getBetterCardRarity(),
                    upgradeCost = it.getCardUpgradeCost(),
                    sellValue = it.getCardSellCost()
                )
            )

        }
    }

    override suspend fun sellCard(cardId: Int) {
        val card = cardDao.getCard(cardId)
        card.collect {
            cardDao.updateCard(
                it.copy(
                    isOwned = false,
                    rarity = null,
                    upgradeCost = Card.UPGRADE_COST_1,
                    sellValue = 0f
                )
            )

        }
    }

    companion object {
        const val CURRENCY_VALUE = "currencyValue"
        const val IS_DATABASE_UP_TO_DATE = "isDatabaseUpToDate"
    }

}