package com.rnm.ricknmortycards.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.compose.events.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState
import com.rnm.ricknmortycards.ui.screen.AllCardsScreen
import com.rnm.ricknmortycards.ui.screen.FavCardsScreen
import com.rnm.ricknmortycards.ui.screen.HomeScreen

@Composable
fun RNMNavigation(
    modifier: Modifier = Modifier,
    allCardsState: List<Card> = emptyList(),
    favCardsState: List<Card> = emptyList(),
    homeState: HomeState? = null,
    onPortalEvent: (PortalEvent) -> Unit,
    onCardEvent: (CardEvent) -> Unit,
    currencyState: Long? = null
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RNMScreen.Home.name
    ) {
        composable(route = RNMScreen.Home.name) {
            HomeScreen(
                modifier = modifier,
                onNavBardEvent = { event ->
                    onNavEvent(event, navController)
                },
                onPortalEvent = onPortalEvent,
                state = homeState,
                currencyState = currencyState
            )
        }
        composable(route = RNMScreen.AllCards.name) {
            AllCardsScreen(
                modifier = modifier,
                onNavBardEvent = { event ->
                    onNavEvent(event, navController)
                },
                allCardsState = allCardsState,
                onCardEvent = onCardEvent,
                currencyState = currencyState
            )
        }
        composable(route = RNMScreen.FavCards.name) {
            FavCardsScreen(
                modifier = modifier,
                onNavBardEvent = { event ->
                    onNavEvent(event, navController)
                },
                favCardsState = favCardsState,
                currencyState = currencyState
            )
        }
    }


}

private fun onNavEvent(event: NavBarEvent, navController: NavController) {
    when (event) {
        NavBarEvent.OnAllCardClicked -> navController.navigate(
            RNMScreen.AllCards.name,
            navOptions { popUpTo(RNMScreen.AllCards.name) { inclusive = true } }
        )
        NavBarEvent.OnFavCardClicked -> navController.navigate(
            RNMScreen.FavCards.name,
            navOptions { popUpTo(RNMScreen.FavCards.name) { inclusive = true } }
        )
        NavBarEvent.OnHomeClicked -> navController.navigate(
            RNMScreen.Home.name,
            navOptions { popUpTo(RNMScreen.Home.name) { inclusive = true } }
        )
    }
}