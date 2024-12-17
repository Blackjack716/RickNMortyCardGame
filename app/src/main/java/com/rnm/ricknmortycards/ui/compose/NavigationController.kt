package com.rnm.ricknmortycards.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rnm.ricknmortycards.ui.screen.AllCardsScreen
import com.rnm.ricknmortycards.ui.screen.FavCardsScreen
import com.rnm.ricknmortycards.ui.screen.HomeScreen

@Composable
fun RNMNavigation(
    modifier: Modifier = Modifier,
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
                }
            )
        }
        composable(route = RNMScreen.AllCards.name) {
            AllCardsScreen(
                modifier = modifier,
                onNavBardEvent = { event ->
                    onNavEvent(event, navController)
                }
            )
        }
        composable(route = RNMScreen.FavCards.name) {
            FavCardsScreen(
                modifier = modifier,
                onNavBardEvent = { event ->
                    onNavEvent(event, navController)
                }
            )
        }
    }


}

private fun onNavEvent(event: NavBarEvent, navController: NavController) {
    when (event) {
        NavBarEvent.OnAllCardClicked -> navController.navigate(RNMScreen.AllCards.name)
        NavBarEvent.OnFavCardClicked -> navController.navigate(RNMScreen.FavCards.name)
        NavBarEvent.OnHomeClicked -> navController.navigate(RNMScreen.Home.name)
    }
}