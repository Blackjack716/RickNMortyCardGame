package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class SetCurrencyUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    fun execute(value: Float) {
        cardRepository.setCurrencyValue(value)
    }
}