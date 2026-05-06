package com.deymervilla.testfakestore.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed

data class FavoriteItemUI(
    val id: Int,
    val imageUrl: String,
    val rating: Float,
    val isFavorite: Boolean = true
)

@Composable
fun FavoriteCard(
    modifier: Modifier = Modifier,
    item: FavoriteItemUI,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .size(
                width = dimensionResource(R.dimen.dimen_height_card),
                height = dimensionResource(R.dimen.dimen_width_card)
            ).clickable { onClick(item.id) },
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_16))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Surface(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.dimen_12))
                    .size(dimensionResource(R.dimen.dimen_32))
                    .align(Alignment.TopEnd),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.8f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = ForeverRed,
                    modifier = Modifier.padding(dimensionResource(R.dimen.dimen_6))
                )
            }
            Surface(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.dimen_12))
                    .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(R.dimen.dimen_8),
                            vertical = dimensionResource(R.dimen.dimen_4)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = ForeverRed,
                        modifier = Modifier.size(dimensionResource(R.dimen.dimen_16))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_4)))
                    Text(
                        text = item.rating.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoriteCardPreview() {
    FakeStoreTheme {
        Box(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.dimen_16))
                .background(MaterialTheme.colorScheme.background)
        ) {
            FavoriteCard(
                item = FavoriteItemUI(
                    id = 1,
                    imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                    rating = 4.5f
                ),
                onClick = {}
            )
        }
    }
}