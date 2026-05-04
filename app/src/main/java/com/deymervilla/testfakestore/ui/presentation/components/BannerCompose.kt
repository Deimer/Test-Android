package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deymervilla.testfakestore.R

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
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.dimen_24),
                    topEnd = dimensionResource(R.dimen.dimen_24)
                ))
        )
        HeaderActionsRowCompose(
            isFavorite = isFavorite,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
        RatingCompose(
            rating = rating,
            reviewCount = reviewCount,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-12).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerComposePreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        BannerCompose(
            imageUrl = "https://example.com/salon_image.jpg",
            rating = 4.8f,
            reviewCount = 1024,
            isFavorite = true,
            onBackClick = {},
            onFavoriteClick = {}
        )
    }
}