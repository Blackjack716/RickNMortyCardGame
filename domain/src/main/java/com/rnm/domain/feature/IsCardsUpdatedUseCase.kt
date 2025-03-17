package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsCardsUpdatedUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    fun execute(): Flow<Boolean> {
        return cardRepository.isDatabaseUpdated()
    }
}