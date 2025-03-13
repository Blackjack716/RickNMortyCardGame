package com.rnm.data.remote.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.rnm.data.local.DataStoreManager
import com.rnm.data.local.dao.CardDao
import com.rnm.data.local.model.toNewCardEntity
import com.rnm.data.remote.api.RickAndMortyApi
import com.rnm.data.remote.model.Characters
import com.rnm.data.remote.model.ErrorResponseType
import com.rnm.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val cardDao: CardDao,
    private val dataStoreManager: DataStoreManager
) : CharactersRepository {

    private val _characters = MutableStateFlow<List<Characters>>(emptyList())
    private var errorType: ErrorResponseType? = null

    override suspend fun updateAllCharacters() {
        errorType = null
        CoroutineScope(Dispatchers.IO).launch {
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
    private fun getCharacters(pagesAmount: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            for (pageNumber in 2..pagesAmount) {
                when (val response = rickAndMortyApi.getNextPageCharacters(pageNumber)) {
                    is NetworkResponse.Success -> {
                        _characters.emit(_characters.value.plus(response.body.results))
                        if (pageNumber == pagesAmount - 1) {
                            addCardsToDatabase()
                        }
                    }

                    is NetworkResponse.ServerError -> errorType = ErrorResponseType.SERVER_ERROR
                    is NetworkResponse.NetworkError -> errorType = ErrorResponseType.NETWORK_ERROR
                    else -> errorType = ErrorResponseType.UNKNOWN_ERROR
                }
            }

        }
    }

    private fun addCardsToDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            _characters.collectLatest {
                if (errorType == null) {
                    cardDao.addCards(it.toNewCardEntity())
                    setCardsAsUpdated()
                }
            }
        }
    }

    private suspend fun setCardsAsUpdated() {
        dataStoreManager.setCardsDatabaseUpdated()
    }
}