package com.rnm.data.remote.repository

import com.rnm.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(

) : CharactersRepository {

    override suspend fun updateAllCharacters() {
        TODO("Not yet implemented")
    }
}