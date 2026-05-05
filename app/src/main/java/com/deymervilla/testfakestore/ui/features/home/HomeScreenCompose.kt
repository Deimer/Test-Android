package com.deymervilla.testfakestore.ui.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.ui.features.alerts.ConnectionErrorScreenCompose
import com.deymervilla.testfakestore.ui.features.alerts.ErrorDetailCompose
import com.deymervilla.testfakestore.ui.features.alerts.LoadingScreenCompose
import com.deymervilla.testfakestore.ui.presentation.components.CardItemUI
import com.deymervilla.testfakestore.ui.presentation.components.CardListCompose
import com.deymervilla.testfakestore.ui.presentation.components.FavoriteItemUI
import com.deymervilla.testfakestore.ui.presentation.components.FavoriteSectionCompose
import com.deymervilla.testfakestore.ui.presentation.components.HomeToolbar

@Composable
fun HomeScreenCompose(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    attributes: HomeScreenAttributes
) {
    val uiState by viewModel.homeUiState.collectAsState()
    val userLocation by viewModel.userLocation.collectAsState()
    val productList by viewModel.productList.collectAsState()
    val favoriteList by viewModel.favoriteList.collectAsState()

    when(uiState) {
        is HomeUiState.Success -> BodyCompose(
            userLocation = userLocation,
            actions = attributes.actions,
            productList = productList,
            favoriteList = favoriteList
        )
        is HomeUiState.Loading -> LoadingScreenCompose()
        is HomeUiState.ConnectionError -> ConnectionErrorScreenCompose()
        is HomeUiState.Error -> {
            val errorMessage = (uiState as HomeUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyCompose(
    userLocation: String? = null,
    actions: HomeScreenActions,
    productList: List<ProductModel>,
    favoriteList: List<ProductModel>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeToolbar(
            location = userLocation,
            searchQuery = "",
            onSearchChange = {},
            onFilterClick = {},
            onProfileClick = { actions.onPrimaryAction },
            onLocationClick = {}
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.dimen_8))
                        .weight(1f)
                )
            }
            item {
                FavoriteSectionCompose(
                    title = stringResource(R.string.your_favorites),
                    items = favoriteList.map {
                        FavoriteItemUI(
                            id = it.id,
                            imageUrl = it.imageUrl,
                            rating = it.ratingLabel
                        )
                    },
                    onSeeAllClick = {},
                    onItemClick = { id -> actions.onSecondaryAction.invoke(id) }
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.dimen_8))
                        .weight(1f)
                )
            }
            item {
                CardListCompose(
                    items = productList.map {
                        CardItemUI(
                            id = it.id,
                            title = it.title,
                            subTitle = it.description,
                            rating = it.ratingLabel,
                            reviewCount = it.ratingCount,
                            imageUrl = it.imageUrl
                        )
                    },
                    onItemClick = { id -> actions.onSecondaryAction.invoke(id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyComposePreview() {
    val mockProducts = listOf(
        ProductModel(
            id = 1,
            title = "Hydrating Facial Serum",
            description = "High-concentration Vitamin C for glowing skin.",
            ratingCount = 4,
            ratingLabel = 1.2f,
            imageUrl = "",
            category = "",
            rawPrice = 20.0
        ),
        ProductModel(
            id = 2,
            title = "Professional Hair Mask",
            description = "Deep repair for damaged hair with keratin.",
            ratingCount = 4,
            ratingLabel = 850f,
            imageUrl = "",
            category = "",
            rawPrice = 20.0
        ),
        ProductModel(
            id = 3,
            title = "Sunscreen SPF 50+",
            description = "Lightweight, non-greasy formula for daily protection.",
            ratingCount = 4,
            ratingLabel = 2.1f,
            imageUrl = "",
            category = "",
            rawPrice = 20.0
        )
    )

    Surface(
        color = Color(0xFFF7F7F7),
        modifier = Modifier.fillMaxSize()
    ) {
        BodyCompose(
            userLocation = "New York, USA",
            actions = HomeScreenActions(
                onPrimaryAction = {},
                onSecondaryAction = {},
                onTertiaryAction = {},
            ),
            productList = mockProducts,
            favoriteList = mockProducts
        )
    }
}