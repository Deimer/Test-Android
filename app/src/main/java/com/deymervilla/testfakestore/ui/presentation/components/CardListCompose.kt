package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R

@Composable
fun CardListCompose(
    modifier: Modifier = Modifier,
    items: List<CardItemUI>,
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_12))
    ) {
        items.forEach { item ->
            CardItemCompose(
                item = item,
                onClick = onItemClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenericCardListPreview() {
    val mockItems = listOf(
        CardItemUI(
            id = 1,
            title = "Vitamin C Serum",
            subTitle = "Glow Labs - 30ml",
            rating = 4.8f,
            reviewCount = 120,
            imageUrl = ""
        ),
        CardItemUI(
            id = 2,
            title = "Anti-Hair Loss Shampoo",
            subTitle = "Professional Care Line",
            rating = 4.7f,
            reviewCount = 500,
            imageUrl = ""
        ),
        CardItemUI(
            id = 3,
            title = "Sunscreen SPF 50",
            subTitle = "Total Protection - All skin types",
            rating = 4.9f,
            reviewCount = 240,
            imageUrl = ""
        )
    )
    Surface(
        color = Color(0xFFF7F7F7),
        modifier = Modifier.fillMaxSize()
    ) {
        CardListCompose(
            items = mockItems,
            onItemClick = { id -> println("Clicked on $id") }
        )
    }
}