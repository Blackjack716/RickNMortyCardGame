package com.rnm.ricknmortycards.ui.compose.sharedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.events.CardEvent
import com.rnm.ricknmortycards.ui.theme.AppTheme
import com.rnm.ricknmortycards.ui.theme.LocalColorScheme

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
            color = AppTheme.colorScheme.primaryTextColor
        )
    }
}

@Composable
fun BoxScope.FavouriteIcon(card: Card, onCardEvent: (CardEvent) -> Unit) {
    var isFav by remember {
        mutableStateOf(card.isFavourite ?: false)
    }

    LaunchedEffect(key1 = card) {
        isFav = card.isFavourite ?: false
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
            color = AppTheme.colorScheme.primaryTextColor,
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