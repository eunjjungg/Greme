package com.shootit.greme.ui.view.compose

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.shootit.greme.R
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shootit.greme.viewmodel.ChallengeGuideViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeCards(viewModel: ChallengeGuideViewModel) {
    val viewModel = remember { viewModel }

    Scaffold {
        Surface(
            modifier = Modifier
                .padding(it),
            color = Color.Transparent
        ) {
            LazyColumn() {
                items(items = viewModel.cardDataList) { item ->
                    MakeCard(cardData = item)
                }
            }
        }
    }

}

@Composable
fun MakeCard(
    cardData: ChallengeGuideViewModel.CardData,
    modifier: Modifier = Modifier
) {
    val cardShape = remember { RoundedCornerShape(8.dp) }
    val mod = remember {
        modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    }
    val cardMod = remember {
        mod
            .padding(start = 4.dp, end = 4.dp, bottom = 24.dp)
    }


    Card(
        shape = cardShape,
        modifier = cardMod,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.greme_main)
        )
    ) {
        Column {
            CardTextContent(
                cardData = cardData,
            )
        }
    }
}

@Composable
private fun CardTextContent(
    cardData: ChallengeGuideViewModel.CardData,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        CardTitle(cardData)
        if(cardData.isOpen.value) {
            CardDetailContent(cardData)
        }
    }
}

@Composable
private fun CardTitle(cardData: ChallengeGuideViewModel.CardData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle(text = cardData.title)
        OpenableChip(cardData)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpenableChip(cardData: ChallengeGuideViewModel.CardData) {
    FilterChip(
        onClick = {
            cardData.isOpen.value = !cardData.isOpen.value
        },
        label = { Text("open") },
        modifier = Modifier
            .padding(end = 8.dp),
        selected = cardData.isOpen.value,
        trailingIcon = {
            Icon(
                imageVector = cardData.isOpen.value.getLeadingIcon(),
                contentDescription = null
            )
        },
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0x2A000000))
    )
}

@Composable
private fun CardDetailContent(cardData: ChallengeGuideViewModel.CardData) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        TextContent(text = cardData.detail)
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextContent(text = "dddd")
    }
}


@Composable
private fun Boolean.getLeadingIcon(): ImageVector {
    return if(this) {
        Icons.Outlined.ExpandLess
    } else {
        Icons.Outlined.ExpandMore
    }
}

