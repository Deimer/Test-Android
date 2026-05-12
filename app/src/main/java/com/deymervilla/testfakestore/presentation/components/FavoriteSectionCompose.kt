package com.deymervilla.testfakestore.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.presentation.theme.ForeverRed

@Composable
fun FavoriteSectionCompose(
    modifier: Modifier = Modifier,
    title: String,
    items: List<FavoriteItemUI>,
    onSeeAllClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.dimen_16),
                    vertical = dimensionResource(R.dimen.dimen_8)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if(items.size > 3) {
                Text(
                    text = stringResource(R.string.see_all),
                    color = ForeverRed,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable { onSeeAllClick() }
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = dimensionResource(R.dimen.dimen_16)
            ),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.dimen_12)
            )
        ) {
            items(items, key = { it.id }) { item ->
                FavoriteCard(
                    item = item,
                    onClick = onItemClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteProductsSectionPreview() {
    val favorites = listOf(
        FavoriteItemUI(1, "", 4.8f),
        FavoriteItemUI(2, "", 4.5f),
        FavoriteItemUI(3, "", 4.9f)
    )

    FavoriteSectionCompose(
        title = "Top Rated Products",
        items = favorites,
        onSeeAllClick = {},
        onItemClick = {}
    )
}