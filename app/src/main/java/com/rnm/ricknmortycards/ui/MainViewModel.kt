package com.rnm.ricknmortycards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rnm.domain.feature.GetAllCardsUseCase
import com.rnm.domain.feature.GetCurrencyUseCase
import com.rnm.domain.feature.GetEnergyRechargeTimeUseCase
import com.rnm.domain.feature.GetEnergyUseCase
import com.rnm.domain.feature.GetFavouriteCardsUseCase
import com.rnm.domain.feature.GetRandomCardUseCase
import com.rnm.domain.feature.SellCardUseCase
import com.rnm.domain.feature.SetCardAsFavUseCase
import com.rnm.domain.feature.SetCurrencyUseCase
import com.rnm.domain.feature.SetEnergyUseCase
import com.rnm.domain.feature.UpdateCharactersUseCase
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState
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
    private val getEnergyLevelUseCase: GetEnergyUseCase,
    private val setEnergyLevelUseCase: SetEnergyUseCase,
    private val getEnergyRechargeTimeUseCase: GetEnergyRechargeTimeUseCase,
    private val setCardAsFavUseCase: SetCardAsFavUseCase,
    private val sellCardUseCase: SellCardUseCase,
    private val setCurrencyUseCase: SetCurrencyUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase
): ViewModel() {

    val homeState = MutableStateFlow(
        HomeState(
            cardState = null,
            energyLevelState = null,
            energyRechargeTimeState = null
        )
    )
    val allCardsState = MutableStateFlow<List<Card>>(emptyList())
    val favCardsState = MutableStateFlow<List<Card>>(emptyList())
    val currencyState = MutableStateFlow(100L)

    init {
        viewModelScope.launch {
            updateCharactersUseCase.execute()
        }
        collectEnergyLevel()
        collectEnergyRechargeTime()
        collectCurrencyValue()
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
                    if (allCardsState.value.isNotEmpty()) {
                        homeState.emit(homeState.value.copy(cardState = getRandomCardUseCase.execute()))
                        setEnergyLevelUseCase.execute(-1)
                    }
                }
            }
            PortalEvent.OnPortalDismissed -> {}
            is PortalEvent.OnPortalSellButtonClicked -> {
                sellCardUseCase.execute(event.cardId)
                setCurrencyUseCase.execute(event.price)
            }
        }
    }

    fun onCardEvent(event: CardEvent) {
        when (event) {
            is CardEvent.OnFavClicked -> {
                setCardAsFavUseCase.execute(event.cardId)
            }
            is CardEvent.OnSellClicked -> {
                sellCardUseCase.execute(event.cardId)
                setCurrencyUseCase.execute(event.price.toFloat())
            }
        }
    }

    private fun collectEnergyLevel() {
        viewModelScope.launch {
            getEnergyLevelUseCase.execute().collectLatest {
                homeState.emit(homeState.value.copy(energyLevelState = it))
            }
        }
    }

    private fun collectEnergyRechargeTime() {
        viewModelScope.launch {
            getEnergyRechargeTimeUseCase.execute().collectLatest {
                homeState.emit(homeState.value.copy(energyRechargeTimeState = it))
            }
        }
    }

    private fun collectCurrencyValue() {
        viewModelScope.launch {
            getCurrencyUseCase.execute().collectLatest {
                currencyState.emit(it.toLong())
            }
        }
    }
}