package com.rnm.domain.feature

import com.rnm.domain.repository.CharactersRepository
import javax.inject.Inject

class UpdateCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val isCardsUpdatedUseCase: IsCardsUpdatedUseCase
) {
    suspend fun execute() {
        if (!isCardsUpdatedUseCase.execute())
            charactersRepository.updateAllCharacters()
    }
}