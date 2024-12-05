package com.rnm.domain.feature

import com.rnm.domain.repository.CharactersRepository
import javax.inject.Inject

class UpdateCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend fun execute() {
        charactersRepository.updateAllCharacters()
    }
}