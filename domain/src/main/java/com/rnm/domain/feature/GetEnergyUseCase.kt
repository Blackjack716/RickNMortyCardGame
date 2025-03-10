package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnergyUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Flow<Int> {
        return cardRepository.getEnergyLevel()
    }
}