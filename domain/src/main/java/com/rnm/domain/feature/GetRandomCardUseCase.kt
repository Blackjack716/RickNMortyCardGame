package com.rnm.domain.feature

import com.rnm.domain.model.Card
import com.rnm.domain.model.Card.Companion.RARITY_1
import com.rnm.domain.model.Card.Companion.RARITY_2
import com.rnm.domain.model.Card.Companion.RARITY_3
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.random.Random

class GetRandomCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Card? {
        val cardCount = cardRepository.getCardCount()
        if (cardCount < 1) {
            return null
        }
        val randomId = Random.nextInt(1, cardCount)
        val rarity = Random.nextInt(0, 100)

        val newRarity = when(rarity) {
            in 0..10 -> RARITY_3
            in 11..30 -> RARITY_2
            else -> RARITY_1
        }

        val sellValue = when(newRarity) {
            RARITY_3 -> Card.SELL_VALUE_3
            RARITY_2 -> Card.SELL_VALUE_2
            else -> Card.SELL_VALUE_1
        }

        val card = cardRepository.getCard(randomId).first()
        val cardRarity: Int = card.rarity ?: 0

        if (card.isOwned == false) {
            cardRepository.upgradeCard(randomId, newRarity)
        } else if (cardRarity < newRarity) {
            cardRepository.upgradeCard(randomId, newRarity)
        } else {
            return card.copy(rarity = newRarity, sellValue = sellValue, upgradeCost = 0f, isDuplicate = true)
        }
        return card.copy(rarity = newRarity, sellValue = sellValue, upgradeCost = 0f)
    }
}