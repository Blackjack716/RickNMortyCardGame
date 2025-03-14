package com.rnm.domain.feature

import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFavouriteCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository,
    private val getSortTypeUseCase: GetSortTypeUseCase
) {
    suspend fun execute(): Flow<List<Card>> {
        return combine(
            cardRepository.getFavCards(),
            getSortTypeUseCase.execute()
        ) { cards, sortType ->
            when (sortType) {
                SortType.Id -> cards.sortedBy { it.id }
                SortType.CharacterName -> cards.sortedBy { it.name }
                SortType.Owned -> cards.sortedBy { it.id }.sortedBy { it.isOwned }
                SortType.Fav -> cards.sortedBy { it.id }.sortedBy { it.isFavourite }
                SortType.Rarity -> cards.sortedBy { it.id }.sortedBy { it.rarity }
            }
        }
    }
}