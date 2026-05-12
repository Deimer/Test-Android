package com.deymervilla.testfakestore.presentation.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.presentation.theme.ForeverRed
import com.deymervilla.testfakestore.presentation.theme.White
import com.deymervilla.testfakestore.presentation.theme.tagButton
import com.deymervilla.testfakestore.utils.toKNotation

@Composable
fun RatingCompose(
    modifier: Modifier = Modifier,
    rating: Float,
    reviewCount: Int
) {
    Surface(
        modifier = modifier,
        color = ForeverRed,
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_20)),
        tonalElevation = dimensionResource(R.dimen.dimen_4)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.dimen_12),
                vertical = dimensionResource(R.dimen.dimen_6)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(dimensionResource(R.dimen.dimen_16))
            )
            Spacer(Modifier.width(dimensionResource(R.dimen.dimen_4)))
            Text(
                text = "$rating (${reviewCount.toKNotation()} ${stringResource(R.string.reviews)})",
                style = tagButton,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingComposePreview() {
    Box(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.dimen_20))
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        RatingCompose(
            rating = 4.8f,
            reviewCount = 1024
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingVariationsPreview() {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_16))
    ) {
        RatingCompose(rating = 5.0f, reviewCount = 5)
        RatingCompose(rating = 3.5f, reviewCount = 999999)
    }
}