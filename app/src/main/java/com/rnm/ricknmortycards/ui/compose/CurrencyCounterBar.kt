package com.rnm.ricknmortycards.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.rnm.ricknmortycards.R

@Composable
fun BoxScope.CurrencyCounterBar(
    currencyState: Long?
) {
    Row(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .size(30.dp),
            imageVector = ImageVector.vectorResource(R.drawable.icon_crystal),
            contentDescription = null
        )
        Text(
            text = currencyState?.toString() ?: "0",
            modifier = Modifier
                .padding(end = 4.dp)
                .align(Alignment.CenterVertically)
        )
    }
}