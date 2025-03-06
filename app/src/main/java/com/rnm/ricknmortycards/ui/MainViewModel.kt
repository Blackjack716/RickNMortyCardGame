package com.rnm.ricknmortycards.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.rnm.domain.feature.GetAllCardsUseCase
import com.rnm.domain.feature.GetFavouriteCardsUseCase
import com.rnm.domain.feature.GetRandomCardUseCase
import com.rnm.domain.feature.UpdateCharactersUseCase
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.PortalEvent
import com.rnm.ricknmortycards.ui.compose.RNMScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateCharactersUseCase: UpdateCharactersUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val getFavouriteCardsUseCase: GetFavouriteCardsUseCase,
    private val getRandomCardUseCase: GetRandomCardUseCase,
): ViewModel() {

    val homeCardState = MutableStateFlow<Card?>(null)
    val allCardsState = MutableStateFlow<List<Card>>(emptyList())
    val favCardsState = MutableStateFlow<List<Card>>(emptyList())

    init {
        viewModelScope.launch {
            updateCharactersUseCase.execute()
        }
    }

    fun collectAllCards() {
        viewModelScope.launch {
            getAllCardsUseCase.execute().collectLatest {
                allCardsState.emit(it)
            }
        }

    }

    fun collectFavCards() {
        viewModelScope.launch {
            getFavouriteCardsUseCase.execute().collectLatest {
                favCardsState.emit(it)
            }
        }
    }

    fun onPortalEvent(event: PortalEvent) {
        when (event) {
            PortalEvent.OnPortalClicked -> {
                viewModelScope.launch {
                    homeCardState.emit(getRandomCardUseCase.execute())
                }
            }
        }
    }
}