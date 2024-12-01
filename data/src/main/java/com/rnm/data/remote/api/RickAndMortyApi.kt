package com.rnm.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.rnm.data.remote.model.CharactersDto
import com.rnm.data.remote.model.ErrorResponse
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("api/character")
    suspend fun getAllCharacters(): NetworkResponse<CharactersDto, ErrorResponse>
}