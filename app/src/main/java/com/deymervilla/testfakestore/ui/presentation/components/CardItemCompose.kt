package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed
import com.deymervilla.testfakestore.ui.presentation.theme.White

data class CardItemUI(
    val id: Int,
    val title: String,
    val subTitle: String,
    val rating: String,
    val reviewCount: String,
    val imageUrl: String
)

@Composable
fun CardItemCompose(
    modifier: Modifier = Modifier,
    item: CardItemUI,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(item.id) },
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_16)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.dimen_2)),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.dimen_12)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.dimen_80))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_12)))
            )
            Spacer(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.dimen_16))
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_4))
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        tint = ForeverRed,
                        modifier = Modifier.size(dimensionResource(R.dimen.dimen_16))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_4)))
                    Text(
                        text = item.subTitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = ForeverRed,
                        modifier = Modifier.size(dimensionResource(R.dimen.dimen_16))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_4)))
                    Text(
                        text = "${item.rating} (${item.reviewCount} Review)",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = ForeverRed
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardItemComposePreview() {
    Box(modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16))) {
        CardItemCompose(
            item = CardItemUI(
                id = 1,
                title = "Hydrating Facial Cream",
                subTitle = "Skincare - 250ml",
                rating = "4.9",
                reviewCount = "850k",
                imageUrl = "https://example.com/image.jpg"
            ),
            onClick = {}
        )
    }
}