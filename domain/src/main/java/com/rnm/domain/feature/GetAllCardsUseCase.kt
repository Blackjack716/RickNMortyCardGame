package com.rnm.domain.feature

import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository,
    private val getSortTypeUseCase: GetSortTypeUseCase
) {
    suspend fun execute(): Flow<List<Card>> {
        return combine(
            cardRepository.getAllCards(),
            getSortTypeUseCase.execute()
        ) { cards, sortType ->
            when (sortType) {
                SortType.Id -> cards.sortedBy { it.id }
                SortType.CharacterName -> cards.sortedBy { it.name }
                SortType.Owned -> cards.sortedBy { it.id }.sortedByDescending { it.isOwned }
                SortType.Fav -> cards.sortedBy { it.id }.sortedByDescending { it.isFavourite }
                SortType.Rarity -> cards.sortedBy { it.id }.sortedByDescending { it.rarity }
            }
        }
    }
}