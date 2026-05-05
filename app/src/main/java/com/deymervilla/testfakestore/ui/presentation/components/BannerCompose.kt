package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.DarkSurface

@Composable
fun BannerCompose(
    modifier: Modifier = Modifier,
    imageUrl: String,
    rating: Float,
    reviewCount: Int,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dimen_height_banner))
            .padding(horizontal = dimensionResource(R.dimen.dimen_16))
            .padding(bottom = dimensionResource(R.dimen.dimen_24))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.dimen_height_banner)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_24)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(R.dimen.dimen_4)
            ),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                HeaderActionsRowCompose(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = dimensionResource(R.dimen.dimen_16)),
                    isFavorite = isFavorite,
                    onBackClick = onBackClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
        RatingCompose(
            rating = rating,
            reviewCount = reviewCount,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = dimensionResource(R.dimen.dimen_14))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BannerComposePreview() {
    BannerCompose(
        imageUrl = "https://example.com/salon_image.jpg",
        rating = 4.8f,
        reviewCount = 1024,
        isFavorite = true,
        onBackClick = {},
        onFavoriteClick = {}
    )
}