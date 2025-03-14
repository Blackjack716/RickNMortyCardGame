package com.rnm.domain.feature

import com.rnm.domain.model.SortType
import com.rnm.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetSortTypeUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    fun execute(sortType: SortType) {
        cardRepository.setSortType(sortType)
    }
}