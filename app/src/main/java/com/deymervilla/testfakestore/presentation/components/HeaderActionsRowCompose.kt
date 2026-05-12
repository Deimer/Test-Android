package com.deymervilla.testfakestore.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.presentation.theme.ForeverRed

@Composable
fun HeaderActionsRowCompose(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_16)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CircleButtonCompose(
            icon = Icons.AutoMirrored.Default.ArrowBack,
            onClick = onBackClick
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.dimen_8)
            )
        ) {
            CircleButtonCompose(
                icon = if(isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                tint = if(isFavorite) ForeverRed else Black,
                onClick = onFavoriteClick
            )
        }
    }
}

@Composable
private fun CircleButtonCompose(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Black
) {
    Surface(
        shape = CircleShape,
        color = Color.White,
        tonalElevation = dimensionResource(R.dimen.dimen_2),
        modifier = modifier
            .size(dimensionResource(R.dimen.dimen_40))
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(dimensionResource(R.dimen.dimen_20)),
                tint = tint
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC)
@Composable
private fun HeaderActionsRowComposePreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.dimen_100))
            .padding(top = dimensionResource(R.dimen.dimen_16))
    ) {
        HeaderActionsRowCompose(
            isFavorite = true,
            onBackClick = { },
            onFavoriteClick = { }
        )
    }
}