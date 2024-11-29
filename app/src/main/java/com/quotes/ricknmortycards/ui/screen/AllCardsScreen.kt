package com.quotes.ricknmortycards.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.quotes.ricknmortycards.ui.compose.NavigationBottomBar

@Preview(showBackground = true)
@Composable
fun AllCardsScreenPreview() {
    AllCardsScreen()
}

@Composable
fun AllCardsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End
        ) {
            Text("Currency: XX")
        }
        LazyColumn {

        }
        NavigationBottomBar()
    }
}