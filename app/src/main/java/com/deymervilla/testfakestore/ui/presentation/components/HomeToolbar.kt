package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onFilterClick: (() -> Unit)? = null,
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
            Column(modifier = Modifier.clickable { onLocationClick() }) {
                Text(
                    text = stringResource(R.string.location),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ForeverRed,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_18))
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_4)))
                    Text(
                        text = location?.takeIf { it.isNotEmpty() } ?: stringResource(R.string.set_location),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (location == null) ForeverRed else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray
                    )
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
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        stringResource(R.string.search_products),
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = ForeverRed)
                },
                shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = ForeverRed
                )
            )
            onFilterClick?.let {
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_12)))
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.dimen_56))
                        .background(ForeverRed, RoundedCornerShape(dimensionResource(R.dimen.dimen_12)))
                        .clickable { onFilterClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.filter),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToolbarLocationPreview() {
    HomeToolbar(
        location = "New York, USA",
        searchQuery = "",
        onSearchChange = {},
        onFilterClick = {},
        onProfileClick = {},
        onLocationClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ToolbarNoLocationPreview() {
    HomeToolbar(
        location = null,
        searchQuery = "",
        onSearchChange = {},
        onFilterClick = {},
        onProfileClick = {},
        onLocationClick = {}
    )
}