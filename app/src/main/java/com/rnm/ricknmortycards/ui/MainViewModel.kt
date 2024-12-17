package com.rnm.ricknmortycards.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rnm.domain.feature.UpdateCharactersUseCase
import com.rnm.ricknmortycards.ui.compose.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.RNMScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateCharactersUseCase: UpdateCharactersUseCase
): ViewModel() {

    val state = mutableStateOf(true)

    init {
        viewModelScope.launch {
            updateCharactersUseCase.execute()
        }
    }

    fun test() {
        println("initializing vm")
    }
}