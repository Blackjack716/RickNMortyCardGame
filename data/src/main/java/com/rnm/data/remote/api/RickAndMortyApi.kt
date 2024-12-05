package com.rnm.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.rnm.data.remote.model.CharactersDto
import com.rnm.data.remote.model.ErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("api/character")
    suspend fun getCharacters(): NetworkResponse<CharactersDto, ErrorResponse>

    @GET("api/character/")
    suspend fun getNextPageCharacters(
        @Query("page") page: Int
    ): NetworkResponse<CharactersDto, ErrorResponse>
}