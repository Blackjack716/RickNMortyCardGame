package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class IsCardsUpdatedUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Boolean {
        return cardRepository.isDatabaseUpdated()
    }
}