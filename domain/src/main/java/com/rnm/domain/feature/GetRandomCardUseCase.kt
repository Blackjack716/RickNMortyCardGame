package com.rnm.domain.feature

import com.rnm.domain.model.Card
import com.rnm.domain.model.Card.Companion.RARITY_1
import com.rnm.domain.model.Card.Companion.RARITY_2
import com.rnm.domain.model.Card.Companion.RARITY_3
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class GetRandomCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Card {
        val cardCount = cardRepository.getCardCount()
        val randomId = Random.nextInt(1, cardCount)
        val rarity = Random.nextInt(0, 100)

        val newRarity = when(rarity) {
            in 0..10 -> RARITY_3
            in 11..30 -> RARITY_2
            else -> RARITY_1
        }

        val card = cardRepository.getCard(randomId).first()
        val cardRarity: Int = card.rarity ?: 0

        if (card.isOwned == false) {
            cardRepository.upgradeCard(randomId)
        } else if (cardRarity < newRarity) {
            cardRepository.upgradeCard(randomId, newRarity)
        }

        return card.copy(rarity = newRarity)
    }
}