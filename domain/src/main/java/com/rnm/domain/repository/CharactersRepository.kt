package com.rnm.domain.repository

interface CharactersRepository {
    suspend fun updateAllCharacters()//jakis response z errorem mozliwym
}