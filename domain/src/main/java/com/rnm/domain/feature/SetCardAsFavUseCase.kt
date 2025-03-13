package com.rnm.domain.feature

import com.rnm.domain.repository.CardRepository
import javax.inject.Inject

class SetCardAsFavUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    fun execute(cardId: Int) {
        cardRepository.setCardAsFav(cardId)
    }
}