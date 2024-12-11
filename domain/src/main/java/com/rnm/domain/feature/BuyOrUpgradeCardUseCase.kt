package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class BuyOrUpgradeCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(cardId: Int) {
        cardRepository.upgradeCard(cardId)
    }
}