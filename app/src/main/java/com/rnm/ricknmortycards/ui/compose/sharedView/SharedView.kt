package com.rnm.ricknmortycards.ui.compose.sharedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rnm.domain.model.Card
import com.rnm.domain.model.SortType
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.events.TopBarEvent
import com.rnm.ricknmortycards.ui.compose.shimmerLoadingAnimation
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState

@Composable
fun ImageOfCharacter(card: Card) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    val greyScaleMatrix = ColorMatrix()
    if (card.isOwned != true) {
        greyScaleMatrix.setToSaturation(0F)
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(card.photoUrl)
            .crossfade(true)
            .build(),
        colorFilter = ColorFilter.colorMatrix(greyScaleMatrix),
        contentDescription = card.name,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .fillMaxSize()
            .then(
                if (isLoading) {
                    Modifier.shimmerLoadingAnimation(
                        isLoadingCompleted = false,
                        isLightModeActive = !isSystemInDarkTheme()
                    )
                } else {
                    Modifier
                }
            ),
        onLoading = {
            isLoading = true
        },
        onSuccess = {
            isLoading = false
        },
        onError = {
            isLoading = false
        },
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopStart
    )
}

@Composable
fun BoxScope.FrameOfCard(card: Card) {
    val imageResource = when (card.rarity) {
        Card.RARITY_1 -> ImageVector.vectorResource(R.drawable.card_frame_blue)
        Card.RARITY_2 -> ImageVector.vectorResource(R.drawable.card_frame_purple)
        Card.RARITY_3 -> ImageVector.vectorResource(R.drawable.card_frame_red)
        else -> ImageVector.vectorResource(R.drawable.card_frame_black)
    }

    Image(
        modifier = Modifier
            .padding(vertical = 0.dp)
            .fillMaxSize(),
        imageVector = imageResource,
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )

    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 9.dp, start = 10.dp)
            .size(width = 22.dp, height = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = card.id.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 8.sp,
            lineHeight = 6.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@Composable
fun BoxScope.FavouriteIcon(card: Card, onCardEvent: (CardEvent) -> Unit) {
    var isFav by remember {
        mutableStateOf(card.isFavourite ?: false)
    }

    if (card.isOwned == true) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 9.dp, end = 10.dp)
                .size(24.dp)
                .clickable {
                    isFav = !isFav
                    val id = card.id
                    if (id != null)
                        onCardEvent(CardEvent.OnFavClicked(id))
                }
        ) {
            if (isFav) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_star_favourite),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_star_no_favourite),
                    contentDescription = null
                )
            }

        }
    }
}

@Composable
fun BoxScope.NameOfCharacter(card: Card) {
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 10.dp, top = 30.dp, start = 10.dp, end = 10.dp)
    ) {
        var cardNameFontSize by remember {
            mutableStateOf(18.sp)
        }
        var readyToDraw by remember {
            mutableStateOf(false)
        }

        Text(
            text = card.name ?: "",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 2.dp)
                .align(Alignment.Center)
                .drawWithContent {
                    if (readyToDraw) drawContent()
                },
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF000000),
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow) {
                    cardNameFontSize *= 0.9f
                } else {
                    readyToDraw = true
                }
            },
            fontSize = cardNameFontSize
        )
    }
}

@Composable
fun ColumnScope.CloseIcon(
    onDismissRequest: () -> Unit
) {
    Image(
        modifier = Modifier
            .padding(4.dp)
            .align(Alignment.End)
            .size(30.dp)
            .border(width = 2.dp, shape = CircleShape, color = Color.LightGray)
            .alpha(0.6f)
            .clickable {
                onDismissRequest()
            },
        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
        contentDescription = null,
    )
}

@Composable
fun ColumnScope.PortalButtons(
    onDismissRequest: () -> Unit = {},
    card: Card?,
    onPortalEvent: (PortalEvent) -> Unit
) {
    ElevatedButton(
        modifier = Modifier
            .widthIn(min = 150.dp, max = 250.dp)
            .align(Alignment.CenterHorizontally),
        onClick = {
            onDismissRequest()
        }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.button_main_ok)
        )
    }

    ElevatedButton(
        modifier = Modifier
            .widthIn(min = 150.dp, max = 250.dp)
            .align(Alignment.CenterHorizontally),
        onClick = {
            onPortalEvent(
                PortalEvent.OnPortalSellButtonClicked(
                    card?.id ?: 0,
                    card?.sellValue ?: 0f
                )
            )
            println("Timer: sell ${card?.id}, ${card?.sellValue}")
            onDismissRequest()
        },
        colors = ButtonColors(
            containerColor = Color(0xFFC96060),
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.button_main_sell)
        )
    }
}

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
            .background(color = Color.Red)
    ) {
        DropdownMenu(
            expanded = isSortMenuExpanded,
            onDismissRequest = { isSortMenuExpanded = false },
            modifier = Modifier
                .background(color = Color.White)
        ) {
            sortMenuOptions.forEach { option ->
                val backgroundColor = if (selectedSortMenuOption == option) {
                    Color.LightGray
                } else {
                    Color.White
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

@Composable
fun BoxScope.ScaleableCardName(
    card: Card?
) {
    var cardNameFontSize by remember {
        mutableStateOf(22.sp)
    }
    var readyToDraw by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = card?.name ?: ("test test test test test test" +
                    "test test test test test test" +
                    "test test test test test test"),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 2.dp)
                .align(Alignment.Center)
                .drawWithContent {
                    if (readyToDraw) drawContent()
                },
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF000000),
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow) {
                    cardNameFontSize *= 0.9f
                } else {
                    readyToDraw = true
                }
            },
            fontSize = cardNameFontSize
        )
    }
}

@Composable
fun BoxScope.CardFrame(
    card: Card?
) {
    val imageResource = when (card?.rarity) {
        Card.RARITY_1 -> ImageVector.vectorResource(R.drawable.card_frame_blue)
        Card.RARITY_2 -> ImageVector.vectorResource(R.drawable.card_frame_purple)
        Card.RARITY_3 -> ImageVector.vectorResource(R.drawable.card_frame_red)
        else -> ImageVector.vectorResource(R.drawable.card_frame_black)
    }

    Image(
        modifier = Modifier
            .padding(vertical = 0.dp)
            .fillMaxSize()
            .align(Alignment.Center),
        imageVector = imageResource,
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )

    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 15.dp, start = 16.dp)
            .size(width = 32.dp, height = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = card?.id.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 8.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@Composable
fun BoxScope.CharacterImage(
    card: Card?
) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    AsyncImage(
        model = card?.photoUrl,
        contentDescription = card?.name,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .align(Alignment.Center)
            .then(
                if (isLoading) {
                    Modifier.shimmerLoadingAnimation(
                        isLoadingCompleted = false,
                        isLightModeActive = !isSystemInDarkTheme()
                    )
                } else {
                    Modifier
                }
            ),
        onLoading = {
            isLoading = true
        },
        onSuccess = {
            isLoading = false
        },
        onError = {
            isLoading = false
        },
        contentScale = ContentScale.Fit,
        alignment = Alignment.TopStart
    )
}