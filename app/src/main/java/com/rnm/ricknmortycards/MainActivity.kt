package com.rnm.ricknmortycards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rnm.ricknmortycards.ui.MainViewModel
import com.rnm.ricknmortycards.ui.compose.RNMNavigation
import com.rnm.ricknmortycards.ui.screen.HomeScreen
import com.rnm.ricknmortycards.ui.theme.RickNMortyCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.test()

        enableEdgeToEdge()
        setContent {
            RickNMortyCardsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RNMNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}