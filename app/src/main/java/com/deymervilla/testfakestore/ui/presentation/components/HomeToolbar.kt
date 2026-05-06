package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed

@Composable
fun HomeToolbar(
    modifier: Modifier = Modifier,
    location: String? = null,
    isLocationLoading: Boolean = false,
    onProfileClick: () -> Unit,
    onLocationClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimen_16))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.clickable(
                    enabled = !isLocationLoading,
                    onClick = onLocationClick
                )
            ) {
                Text(
                    text = stringResource(R.string.location),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnimatedContent(
                        targetState = isLocationLoading,
                        transitionSpec = {
                            (fadeIn(tween(200)) + scaleIn(initialScale = 0.8f)) togetherWith
                                    (fadeOut(tween(200)) + scaleOut(targetScale = 0.8f))
                        },
                        label = "locationIcon"
                    ) { loading ->
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_18)),
                                strokeWidth = dimensionResource(R.dimen.dimen_2),
                                color = ForeverRed,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = ForeverRed,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_18))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_4)))
                    Text(
                        text = when {
                            isLocationLoading -> stringResource(R.string.getting_location)
                            location.isNullOrEmpty() -> stringResource(R.string.set_location)
                            else -> location
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            isLocationLoading -> Color.Gray
                            location.isNullOrEmpty() -> ForeverRed
                            else -> Color.Black
                        }
                    )
                    if(isLocationLoading.not()) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .background(Color(0xFFF7F7F7), CircleShape)
                    .size(dimensionResource(R.dimen.dimen_40))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.profile),
                    tint = Color.Black
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ToolbarLocationPreview() {
    HomeToolbar(
        location = "New York, USA",
        onProfileClick = {},
        onLocationClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ToolbarNoLocationPreview() {
    HomeToolbar(
        location = null,
        onProfileClick = {},
        onLocationClick = {}
    )
}