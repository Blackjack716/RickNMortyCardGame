package com.rnm.ricknmortycards.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.rnm.domain.feature.GetAllCardsUseCase
import com.rnm.domain.feature.GetEnergyRechargeTimeUseCase
import com.rnm.domain.feature.GetEnergyUseCase
import com.rnm.domain.feature.GetFavouriteCardsUseCase
import com.rnm.domain.feature.GetRandomCardUseCase
import com.rnm.domain.feature.SetEnergyUseCase
import com.rnm.domain.feature.UpdateCharactersUseCase
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.PortalEvent
import com.rnm.ricknmortycards.ui.compose.RNMScreen
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
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
): ViewModel() {

    val homeCardState = MutableStateFlow<Card?>(null)
    val homeState = MutableStateFlow(
        HomeState(
            cardState = null,
            energyLevelState = null,
            energyRechargeTimeState = null
        )
    )
    val allCardsState = MutableStateFlow<List<Card>>(emptyList())
    val favCardsState = MutableStateFlow<List<Card>>(emptyList())

    init {
        viewModelScope.launch {
            updateCharactersUseCase.execute()
        }
        collectEnergyLevel()
        collectEnergyRechargeTime()
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
                    homeState.emit(homeState.value.copy(cardState = getRandomCardUseCase.execute()))
                    val energyLevel = getEnergyLevelUseCase.execute().first()
                    setEnergyLevelUseCase.execute(energyLevel - 1)
                }
            }
            PortalEvent.OnPortalDismissed -> {

            }
            is PortalEvent.OnPortalSellButtonClicked -> TODO()
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
}