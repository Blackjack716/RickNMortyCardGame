package com.rnm.ricknmortycards.ui.compose.sharedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.rnm.domain.model.SortType
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.events.TopBarEvent
import com.rnm.ricknmortycards.ui.theme.AppTheme
import com.rnm.ricknmortycards.ui.theme.LocalColorScheme

@Composable
fun BoxScope.SortMenu(
    onTopBarEvent: (TopBarEvent) -> Unit
) {
    val sortMenuOptions = listOf(
        SortType.Id,
        SortType.CharacterName,
        SortType.Rarity,
        SortType.Owned,
    )
    var isSortMenuExpanded by remember { mutableStateOf(false) }
    var selectedSortMenuOption by remember { mutableStateOf(sortMenuOptions[0]) }
    val colorScheme = AppTheme.colorScheme

    Row(
        modifier = Modifier
            .padding(top = 36.dp)
            .align(Alignment.TopEnd)
            .clickable {
                isSortMenuExpanded = true
            },
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = stringResource(R.string.sort_cards),
            modifier = Modifier
                .padding(end = 4.dp)
                .align(Alignment.CenterVertically)
        )
        Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 8.dp, top = 8.dp, bottom = 4.dp)
                .size(12.dp),
            imageVector = ImageVector.vectorResource(R.drawable.arrow_down_list),
            contentDescription = null
        )


    }
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 36.dp)
            .widthIn(min = 80.dp, max = 180.dp)
            .heightIn(1.dp)
    ) {
        DropdownMenu(
            expanded = isSortMenuExpanded,
            onDismissRequest = { isSortMenuExpanded = false },
            modifier = Modifier
                .background(color = colorScheme.backgroundColor)
        ) {
            sortMenuOptions.forEach { option ->
                val backgroundColor = if (selectedSortMenuOption == option) {
                    colorScheme.secondaryBackgroundColor
                } else {
                    colorScheme.backgroundColor
                }
                DropdownMenuItem(
                    text = {
                        when(option) {
                            SortType.Id -> Text(text = stringResource(R.string.sort_menu_option1))
                            SortType.CharacterName -> Text(text = stringResource(R.string.sort_menu_option2))
                            SortType.Rarity -> Text(text = stringResource(R.string.sort_menu_option3))
                            SortType.Owned -> Text(text = stringResource(R.string.sort_menu_option4))
                            SortType.Fav -> Text(text = stringResource(R.string.sort_menu_option5))
                            else -> Text(text = stringResource(R.string.sort_menu_option1))
                        }
                    },
                    onClick = {
                        selectedSortMenuOption = option
                        isSortMenuExpanded = false
                        onTopBarEvent(TopBarEvent.OnSortClicked(selectedSortMenuOption))
                    },
                    modifier = Modifier
                        .background(color = backgroundColor)
                )
            }
        }
    }
}