package com.rnm.ricknmortycards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rnm.ricknmortycards.ui.MainViewModel
import com.rnm.ricknmortycards.ui.compose.RNMNavigation
import com.rnm.ricknmortycards.ui.theme.RickNMortyCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.collectAllCards()
        mainViewModel.collectFavCards()


        enableEdgeToEdge()
        setContent {

            var allCardsState by remember {
                mutableStateOf(mainViewModel.allCardsState.value)
            }
            var favCardsState by remember {
                mutableStateOf(mainViewModel.favCardsState.value)
            }
            var homeState by remember {
                mutableStateOf(mainViewModel.homeState.value)
            }
            var currencyState by remember {
                mutableLongStateOf(mainViewModel.currencyState.value)
            }

            LaunchedEffect(mainViewModel.allCardsState) {
                mainViewModel.allCardsState.collect {
                    allCardsState = it
                }

            }
            LaunchedEffect(mainViewModel.favCardsState) {
                mainViewModel.favCardsState.collect {
                    favCardsState = it
                }

            }
            LaunchedEffect(mainViewModel.homeState) {
                mainViewModel.homeState.collect {
                    homeState = it
                }

            }
            LaunchedEffect(mainViewModel.currencyState) {
                mainViewModel.currencyState.collect {
                    currencyState = it
                }
            }

            RickNMortyCardsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RNMNavigation(
                        modifier = Modifier.padding(innerPadding),
                        allCardsState = allCardsState,
                        favCardsState = favCardsState,
                        homeState = homeState,
                        onPortalEvent = mainViewModel::onPortalEvent,
                        onCardEvent = mainViewModel::onCardEvent,
                        currencyState = currencyState,
                        onTopBarEvent = mainViewModel::onTopBarEvent
                    )
                }
            }
        }
    }
}