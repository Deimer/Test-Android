package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.dimen_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_12))
    ) {
        items(items, key = { it.id }) { item ->
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
            rating = "4.8",
            reviewCount = "1.2k",
            imageUrl = ""
        ),
        CardItemUI(
            id = 2,
            title = "Anti-Hair Loss Shampoo",
            subTitle = "Professional Care Line",
            rating = "4.7",
            reviewCount = "500",
            imageUrl = ""
        ),
        CardItemUI(
            id = 3,
            title = "Sunscreen SPF 50",
            subTitle = "Total Protection - All skin types",
            rating = "4.9",
            reviewCount = "2.1k",
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