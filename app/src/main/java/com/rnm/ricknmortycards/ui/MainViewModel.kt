package com.rnm.ricknmortycards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rnm.domain.feature.BuyOrUpgradeCardUseCase
import com.rnm.domain.feature.GetAllCardsUseCase
import com.rnm.domain.feature.GetCurrencyUseCase
import com.rnm.domain.feature.GetEnergyRechargeTimeUseCase
import com.rnm.domain.feature.GetEnergyUseCase
import com.rnm.domain.feature.GetFavouriteCardsUseCase
import com.rnm.domain.feature.GetRandomCardUseCase
import com.rnm.domain.feature.GetSortTypeUseCase
import com.rnm.domain.feature.SellCardUseCase
import com.rnm.domain.feature.SetCardAsFavUseCase
import com.rnm.domain.feature.SetCurrencyUseCase
import com.rnm.domain.feature.SetEnergyUseCase
import com.rnm.domain.feature.SetSortTypeUseCase
import com.rnm.domain.feature.UpdateCharactersUseCase
import com.rnm.domain.model.SortType
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.events.TopBarEvent
import com.rnm.ricknmortycards.ui.compose.uiState.AllCardsState
import com.rnm.ricknmortycards.ui.compose.uiState.FavCardsState
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
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getSortTypeUseCase: GetSortTypeUseCase,
    private val setSortTypeUseCase: SetSortTypeUseCase,
    private val buyOrUpgradeCardUseCase: BuyOrUpgradeCardUseCase
): ViewModel() {

    val homeState = MutableStateFlow(
        HomeState(
            cardState = null,
            energyLevelState = null,
            energyRechargeTimeState = null
        )
    )
    val allCardsState = MutableStateFlow(AllCardsState(emptyList(), SortType.Id))
    val favCardsState = MutableStateFlow(FavCardsState(emptyList(), SortType.Id))
    val currencyState = MutableStateFlow(0L)

    init {
        viewModelScope.launch {
            updateCharactersUseCase.execute()
        }
        collectEnergyLevel()
        collectEnergyRechargeTime()
        collectCurrencyValue()
        collectSortType()
    }

    fun collectAllCards() {
        viewModelScope.launch {
            getAllCardsUseCase.execute().collectLatest {
                allCardsState.emit(allCardsState.value.copy(cards = it))
            }
        }

    }

    fun collectFavCards() {
        viewModelScope.launch {
            getFavouriteCardsUseCase.execute().collectLatest {
                favCardsState.emit(favCardsState.value.copy(cards = it))
            }
        }
    }

    fun onPortalEvent(event: PortalEvent) {
        when (event) {
            PortalEvent.OnPortalClicked -> {
                viewModelScope.launch {
                    if (allCardsState.value.cards.isNotEmpty()) {
                        val card = getRandomCardUseCase.execute()
                        println("card: $card")
                        homeState.emit(homeState.value.copy(cardState = card))
                        if (card != null) {
                            setEnergyLevelUseCase.execute(-1)
                        }
                    }
                }
            }
            PortalEvent.OnPortalDismissed -> {}
            is PortalEvent.OnPortalSellButtonClicked -> {
                sellCardUseCase.execute(event.cardId)
                setCurrencyUseCase.execute(event.price)
            }
            is PortalEvent.OnPortalUpgradeButtonClicked -> {
                buyOrUpgradeCardUseCase.execute(event.cardId)
                setCurrencyUseCase.execute(event.price * -1)
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

    fun onTopBarEvent(event: TopBarEvent) {
        when (event) {
            is TopBarEvent.OnSortClicked -> {
                setSortTypeUseCase.execute(event.sortType)
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

    private fun collectSortType() {
        viewModelScope.launch {
            getSortTypeUseCase.execute().collectLatest {
                allCardsState.emit(allCardsState.value.copy(sortType = it))
                favCardsState.emit(favCardsState.value.copy(sortType = it))
            }
        }
    }
}