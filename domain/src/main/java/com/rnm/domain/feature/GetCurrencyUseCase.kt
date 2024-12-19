package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Flow<Float> {
        return cardRepository.getCurrencyValue()
    }
}