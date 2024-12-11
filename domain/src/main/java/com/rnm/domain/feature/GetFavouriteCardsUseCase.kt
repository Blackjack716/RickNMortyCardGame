package com.rnm.domain.feature

import com.rnm.domain.model.Card
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Flow<List<Card>> {
        return cardRepository.getFavCards()
    }
}