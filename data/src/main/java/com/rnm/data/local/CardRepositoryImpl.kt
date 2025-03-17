package com.rnm.data.local

import com.rnm.data.local.dao.CardDao
import com.rnm.data.local.model.getBetterCardRarity
import com.rnm.data.local.model.getCardSellCost
import com.rnm.data.local.model.getCardUpgradeCost
import com.rnm.data.local.model.toCard
import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType
import com.rnm.domain.model.toSortType
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
        return cardDao.getFavouriteCards()
            .map { it.toCard() }
            .map { it.filter { card -> card.isOwned == true } }
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

    override fun isDatabaseUpdated(): Flow<Boolean> {
        return dataStoreManager.getIsDatabaseUpdated()
    }

    override fun upgradeCard(cardId: Int, rarity: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            val card = cardDao.getCard(cardId).first()
            if (rarity != null) {
                cardDao.updateCard(
                    card.copy(
                        isOwned = true,
                        rarity = rarity,
                        upgradeCost = card.getCardUpgradeCost(rarity),
                        sellValue = card.getCardSellCost(rarity),
                    )
                )
            } else {
                val newRarity = card.getBetterCardRarity()
                cardDao.updateCard(
                    card.copy(
                        isOwned = true,
                        rarity = card.getBetterCardRarity(),
                        upgradeCost = card.getCardUpgradeCost(newRarity),
                        sellValue = card.getCardSellCost(newRarity),
                    )
                )
            }
        }
    }

    override fun sellCard(cardId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val card = cardDao.getCard(cardId).first()
            cardDao.updateCard(
                card.copy(
                    isOwned = false,
                    rarity = null,
                    upgradeCost = Card.UPGRADE_COST_1,
                    sellValue = 0f
                )
            )
        }
    }

    override suspend fun getCardCount(): Int {
        if (cardCount.value == null || cardCount.value == 0) {
            return cardDao.getAllCards().map { it.size }.first()
        }
        return cardCount.value ?: cardDao.getAllCards().map { it.size }.first()
    }

    override suspend fun getCard(cardId: Int): Flow<Card> {
        return cardDao.getCard(cardId).map { it.toCard() }
    }

    override suspend fun getEnergyLevel(): Flow<Int> {
        return energyManager.getEnergyLevel()
    }

    override fun setEnergyLevel(energyLevel: Int) {
        energyManager.setEnergyLevel(energyLevel)
    }

    override suspend fun getEnergyRechargeTime(): Flow<Long> {
        return dataStoreManager.getEnergyRechargeTime()
    }

    override fun setSortType(sortType: SortType) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.setSortType(sortType)
        }
    }

    override suspend fun getSortType(): Flow<SortType> {
        return dataStoreManager.getSortType().map { it.toSortType() }
    }

}