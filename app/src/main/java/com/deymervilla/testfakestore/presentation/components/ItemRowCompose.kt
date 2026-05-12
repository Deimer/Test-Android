package com.deymervilla.testfakestore.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.presentation.theme.FakeStoreTheme
import com.deymervilla.testfakestore.presentation.theme.White
import com.deymervilla.testfakestore.utils.capitalizeWords

@Composable
fun ItemRowCompose(
    modifier: Modifier = Modifier,
    title: String,
    trailingText: String? = null,
    trailingIcon: ImageVector? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            ),
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.dimen_dot_5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.dimen_16),
                    vertical = dimensionResource(R.dimen.dimen_14)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            trailingText?.let {
                Text(
                    text = it.capitalizeWords(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (trailingIcon != null) {
                Spacer(Modifier.width(dimensionResource(R.dimen.dimen_8)))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = iconTint
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun ItemRowComposePreview() {
    FakeStoreTheme {
        ItemRowCompose(
            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16)),
            title = "Hair Soap"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun ItemRowComposeWithTrailingTextPreview() {
    FakeStoreTheme {
        ItemRowCompose(
            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16)),
            title = "Hair Soap",
            trailingText = "12 Types"
        )
    }
}