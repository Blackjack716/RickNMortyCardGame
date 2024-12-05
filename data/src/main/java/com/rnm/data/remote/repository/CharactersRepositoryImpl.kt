package com.rnm.data.remote.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.rnm.data.remote.api.RickAndMortyApi
import com.rnm.data.remote.model.Characters
import com.rnm.data.remote.model.ErrorResponseType
import com.rnm.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) : CharactersRepository {

    private val _characters = MutableStateFlow<List<Characters>>(emptyList())
    private var errorType: ErrorResponseType? = null

    override suspend fun updateAllCharacters() {
        errorType = null
        CoroutineScope(Dispatchers.IO).launch {
            //delay(5000)
            when (val response = rickAndMortyApi.getCharacters()) {
                is NetworkResponse.Success -> {
                    _characters.emit(response.body.results)

                    val pagesAmount = response.body.info?.pages
                    if (pagesAmount != null) {
                        getCharacters(pagesAmount)
                    }
                }

                is NetworkResponse.ServerError -> errorType = ErrorResponseType.SERVER_ERROR
                is NetworkResponse.NetworkError -> errorType = ErrorResponseType.NETWORK_ERROR
                else -> errorType = ErrorResponseType.UNKNOWN_ERROR
            }
        }
    }

    /**
     * First call to api got no nextPage parameter,
     * if there is pages parameter then call for all pages, starting from 2nd page
     */
    private suspend fun getCharacters(pagesAmount: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            for (pageNumber in 2..pagesAmount) {
                when (val response = rickAndMortyApi.getNextPageCharacters(pageNumber)) {
                    is NetworkResponse.Success -> {
                        _characters.emit(_characters.value.plus(response.body.results))
                    }

                    is NetworkResponse.ServerError -> errorType = ErrorResponseType.SERVER_ERROR
                    is NetworkResponse.NetworkError -> errorType = ErrorResponseType.NETWORK_ERROR
                    else -> errorType = ErrorResponseType.UNKNOWN_ERROR
                }
            }

        }
    }
}