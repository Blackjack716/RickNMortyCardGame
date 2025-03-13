package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class SetEnergyUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    fun execute(energyLevel: Int) {
        cardRepository.setEnergyLevel(energyLevel)
    }
}