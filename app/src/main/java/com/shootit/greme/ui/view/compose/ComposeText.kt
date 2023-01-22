package com.shootit.greme.ui.view.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shootit.greme.R

@Composable
fun TextTitle(
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.headlineMedium.copy(
            color = colorResource(id = R.color.text),
            fontSize = dpToSp(14.dp)
        )
    )
}

@Composable
fun TextContent(
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 12.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = colorResource(id = R.color.text),
            fontSize = dpToSp(11.dp)
        )
    )
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }