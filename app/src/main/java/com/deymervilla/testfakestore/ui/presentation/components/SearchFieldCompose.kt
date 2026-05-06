package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed
import com.deymervilla.testfakestore.ui.presentation.theme.LightSurface

data class SearchItemUi(
    val id: Int,
    val name: String,
    val isFavorite: Boolean = false,
    val metadata: String? = null
)

@Composable
fun SearchFieldCompose(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onFilterClick: (() -> Unit)? = null,
    searchResults: List<SearchItemUi>,
    onItemClick: (Int) -> Unit
) {
    Box(modifier = modifier) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        Column {
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
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    onSearchChange("")
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = LightSurface,
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
            if (searchQuery.isNotEmpty() && searchResults.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.dimen_4))
                        .zIndex(10f),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = dimensionResource(R.dimen.dimen_8)
                    ),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        searchResults.take(7).forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        keyboardController?.hide()
                                        onItemClick(item.id)
                                    }.padding(dimensionResource(R.dimen.dimen_16)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if(item.isFavorite) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = ForeverRed,
                                        modifier = Modifier.size(dimensionResource(R.dimen.dimen_16)))
                                } else {
                                    Spacer(modifier = Modifier.size(dimensionResource(R.dimen.dimen_16)))
                                }
                                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_12)))
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(dimensionResource(R.dimen.dimen_18))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFieldWithDropdownEmptyPreview() {
    FakeStoreTheme {
        Box(modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16))) {
            SearchFieldCompose(
                searchQuery = "",
                onSearchChange = {},
                searchResults = emptyList(),
                onItemClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFieldWithDropdownResultsPreview() {
    val mockResults = listOf(
        SearchItemUi(id = 1, name = "Cotton T-shirt", isFavorite = true),
        SearchItemUi(id = 2, name = "Sweatpants", isFavorite = false),
        SearchItemUi(id = 3, name = "Running shoes", isFavorite = true),
        SearchItemUi(id = 4, name = "Winter jacket", isFavorite = false)
    )
    FakeStoreTheme {
        Box(modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16))) {
            SearchFieldCompose(
                searchQuery = "Cotton",
                onSearchChange = {},
                onFilterClick = {},
                searchResults = mockResults,
                onItemClick = {}
            )
        }
    }
}