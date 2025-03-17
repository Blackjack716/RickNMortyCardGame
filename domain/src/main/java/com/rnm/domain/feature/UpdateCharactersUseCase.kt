package com.rnm.domain.feature

import com.rnm.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val isCardsUpdatedUseCase: IsCardsUpdatedUseCase
) {
    suspend fun execute() {
        if (!isCardsUpdatedUseCase.execute().first())
            charactersRepository.updateAllCharacters()
    }
}