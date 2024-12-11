package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    fun execute(): Float {
        return cardRepository.getCurrencyValue()
    }
}