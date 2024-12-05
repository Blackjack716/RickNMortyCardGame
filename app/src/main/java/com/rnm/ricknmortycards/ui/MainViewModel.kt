package com.rnm.ricknmortycards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rnm.domain.feature.UpdateCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateCharactersUseCase: UpdateCharactersUseCase
): ViewModel() {

    init {
        viewModelScope.launch {
            updateCharactersUseCase.execute()
        }
    }

    fun test() {
        println("initializing vm")
    }
}