package com.rnm.domain.feature

import com.rnm.domain.model.SortType
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortTypeUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend fun execute(): Flow<SortType> {
        return cardRepository.getSortType()
    }
}