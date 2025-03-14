package com.rnm.ricknmortycards.ui.compose.sharedView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.shimmerLoadingAnimation

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
    onPortalEvent: (PortalEvent) -> Unit,
    crystals: Long?
) {
    val upgradeCost = card?.upgradeCost
    if (upgradeCost != null && crystals != null && upgradeCost.toInt() != 0) {
        ElevatedButton(
            modifier = Modifier
                .widthIn(min = 150.dp, max = 250.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                if (crystals >= upgradeCost) {
                    onPortalEvent(
                        PortalEvent.OnPortalUpgradeButtonClicked(
                            card.id ?: 0,
                            upgradeCost
                        )
                    )
                    onDismissRequest()
                }
            },
            enabled = crystals >= upgradeCost,
            colors = ButtonColors(
                containerColor = Color(0xFF77D756),
                contentColor = Color.Black,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color(0xFF434F3E)
            )
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.button_main_upgrade, upgradeCost.toInt())
            )
            Image(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .size(18.dp),
                imageVector = ImageVector.vectorResource(R.drawable.icon_crystal),
                contentDescription = null
            )
        }
    } else {
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
            text = stringResource(R.string.button_main_sell, (card?.sellValue?.toInt() ?: 0))
        )
        Image(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .size(18.dp),
            imageVector = ImageVector.vectorResource(R.drawable.icon_crystal),
            contentDescription = null
        )
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

    if (card?.isDuplicate == true) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .align(Alignment.Center)
                .background(color = Color(0xB9545454)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.card_duplicated),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (card.sellValue != null) {
                    Text(
                        text = "+${card.sellValue?.toInt() ?: 0}",
                    )
                    Image(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .size(18.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.icon_crystal),
                        contentDescription = null
                    )
                }

            }
        }
    }
}