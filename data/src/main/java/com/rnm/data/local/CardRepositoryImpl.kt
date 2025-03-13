package com.rnm.data.local

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val cardDao: CardDao
) : CardRepository {

    private val energyManager: EnergyManager = EnergyManager(dataStoreManager)

    init {
        energyManager.start()
    }

    private var cardCount: MutableStateFlow<Int?> = MutableStateFlow(null)

    override suspend fun getAllCards(): Flow<List<Card>> {
        val cards = cardDao.getAllCards()
        cardCount.value = cards.map { it.size }.first()
        return cards.map { it.toCard() }
    }

    override suspend fun getFavCards(): Flow<List<Card>> {
        return cardDao.getFavouriteCards().map { it.toCard() }
    }

    override fun setCardAsFav(cardId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val card = cardDao.getCard(cardId).first()
            val isFav = card.isFavourite ?: false
            cardDao.updateCard(card.copy(isFavourite = !isFav))
        }
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

    override suspend fun upgradeCard(cardId: Int, rarity: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            val card = cardDao.getCard(cardId).first()
            if (rarity != null) {
                cardDao.updateCard(
                    card.copy(
                        rarity = rarity,
                        upgradeCost = card.getCardUpgradeCost(rarity),
                        sellValue = card.getCardSellCost(rarity)
                    )
                )
            } else {
                cardDao.updateCard(
                    card.copy(
                        isOwned = true,
                        rarity = card.getBetterCardRarity(),
                        upgradeCost = card.getCardUpgradeCost(),
                        sellValue = card.getCardSellCost()
                    )
                )
            }
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

    override suspend fun getCardCount(): Int {
        return cardCount.value ?: cardDao.getAllCards().map { it.size }.first()
    }

    override suspend fun getCard(cardId: Int): Flow<Card> {
        return cardDao.getCard(cardId).map { it.toCard() }
    }

    override suspend fun getEnergyLevel(): Flow<Int> {
        return energyManager.getEnergyLevel()
    }

    override suspend fun setEnergyLevel(energyLevel: Int) {
        energyManager.setEnergyLevel(energyLevel)
    }

    override suspend fun getEnergyRechargeTime(): Flow<Long> {
        return dataStoreManager.getEnergyRechargeTime()
    }


    companion object {
        const val CURRENCY_VALUE = "currencyValue"
        const val IS_DATABASE_UP_TO_DATE = "isDatabaseUpToDate"
    }

}