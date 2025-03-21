package com.rnm.ricknmortycards.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.rnm.domain.model.Card
import com.rnm.ricknmortycards.R
import com.rnm.ricknmortycards.ui.compose.AnimatedBackgroundAura
import com.rnm.ricknmortycards.ui.compose.CurrencyCounterBar
import com.rnm.ricknmortycards.ui.compose.NavigationBottomBar
import com.rnm.ricknmortycards.ui.compose.events.NavBarEvent
import com.rnm.ricknmortycards.ui.compose.events.PortalEvent
import com.rnm.ricknmortycards.ui.compose.sharedView.CardFrame
import com.rnm.ricknmortycards.ui.compose.sharedView.CharacterImage
import com.rnm.ricknmortycards.ui.compose.sharedView.CloseIcon
import com.rnm.ricknmortycards.ui.compose.sharedView.PortalButtons
import com.rnm.ricknmortycards.ui.compose.sharedView.ScaleableCardName
import com.rnm.ricknmortycards.ui.compose.uiState.HomeState
import com.rnm.ricknmortycards.ui.theme.AppColorScheme
import com.rnm.ricknmortycards.ui.theme.LocalColorScheme
import com.rnm.ricknmortycards.ui.theme.RickNMortyCardsTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val HOUR_IN_MILLIS = 3600000L

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onNavBardEvent = {},
        onPortalEvent = {},
        state = null,
        currencyState = null
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavBardEvent: (NavBarEvent) -> Unit,
    state: HomeState?,
    onPortalEvent: (PortalEvent) -> Unit,
    currencyState: Long?
) {
    var openPortal by remember {
        mutableStateOf(false)
    }

    if (openPortal) {
        if (state?.cardState != null) {
            CardDialog(
                onDismissRequest = { openPortal = false },
                card = state.cardState,
                onPortalEvent = onPortalEvent,
                crystals = currencyState
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Snackbar(
                    modifier = Modifier
                        .padding(top = 60.dp)
                ) {
                    Text(
                        text = stringResource(R.string.snackbar_no_card)
                    )
                    LaunchedEffect(true) {
                        delay(5000)
                        openPortal = false
                    }
                }
            }

        }

    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CurrencyCounterBar(currencyState)
            PortalAnimation(
                onPortalClicked = {
                    if ((state?.energyLevelState ?: 0) > 0) {
                        onPortalEvent(PortalEvent.OnPortalClicked)
                        openPortal = true
                    }
                }
            )
            BackgroundGraphic(state)
            NavigationBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onEvent = onNavBardEvent
            )

        }
    }
}

@Composable
private fun BoxScope.PortalAnimation(onPortalClicked: () -> Unit) {
    Column(
        modifier = Modifier.align(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = R.drawable.portal_home,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 120.dp)
                .fillMaxWidth(0.7f)
                .clickable {
                    onPortalClicked()
                }
        )
    }
}

@Composable
private fun BoxScope.BackgroundGraphic(state: HomeState?) {
    var rechargingTime by remember {
        mutableStateOf(convertLongToTimeLeft(state?.energyRechargeTimeState))
    }

    var isRecharging by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state?.energyLevelState) {
        val energyLevel = state?.energyLevelState ?: 0
        if (energyLevel >= 10) {
            isRecharging = false
        } else {
            isRecharging = true
            while (true) {
                delay(1000)
                rechargingTime = convertLongToTimeLeft(state?.energyRechargeTimeState)
            }
        }
    }

    val rechargingText = if (!isRecharging) {
        stringResource(R.string.recharging_text_full)
    } else {
        stringResource(R.string.recharging_text, rechargingTime)
    }


    Image(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 100.dp),
        imageVector = ImageVector.vectorResource(R.drawable.home_background),
        contentDescription = null
    )
    Image(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp)
            .fillMaxWidth(),
        imageVector = ImageVector.vectorResource(R.drawable.portalgun_home),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
    Image(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 190.dp)
            .width(80.dp),
        imageVector = ImageVector.vectorResource(R.drawable.glassfluidgreen_home),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
    Text(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(bottom = 85.dp, start = 44.dp, end = 44.dp)
            .fillMaxWidth()
            .graphicsLayer(rotationX = 20f),
        text = stringResource(R.string.energy_level_info, state?.energyLevelState ?: 0),
        color = Color.Red
    )
    Text(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 85.dp, end = 44.dp, start = 44.dp)
            .fillMaxWidth()
            .graphicsLayer(rotationX = 20f),
        text = rechargingText,
        color = Color.Red,
        textAlign = TextAlign.End
    )
}

@Composable
fun CardDialog(
    onDismissRequest: () -> Unit = {},
    card: Card?,
    onPortalEvent: (PortalEvent) -> Unit,
    crystals: Long?
) {
    Dialog(onDismissRequest = onDismissRequest) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LocalColorScheme.current.backgroundColor)
        ) {
            CloseIcon(onDismissRequest)
            Box(
                modifier = Modifier
                    .size(width = 192.dp, height = 280.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Transparent)
            ) {
                AnimatedBackgroundAura(
                    rarity = card?.rarity,
                    animationTime = 2000
                )
                CharacterImage(card)
                CardFrame(card)
                ScaleableCardName(card)
            }

            PortalButtons(
                onDismissRequest = onDismissRequest,
                card = card,
                onPortalEvent = onPortalEvent,
                crystals = crystals
            )
        }

    }
}

private fun convertLongToTimeLeft(time: Long?): String {
    if (time == null) return ""

    val currentTime = System.currentTimeMillis()
    val remaining = time - currentTime
    val date = Date(time - currentTime)
    val format = if (remaining >= HOUR_IN_MILLIS)
        SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    else
        SimpleDateFormat("mm:ss", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}

