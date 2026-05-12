package com.deymervilla.testfakestore.features.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.deymervilla.repository.models.ProductModel
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.features.alerts.ErrorDetailCompose
import com.deymervilla.testfakestore.features.alerts.LoadingScreenCompose
import com.deymervilla.testfakestore.presentation.components.BannerCompose
import com.deymervilla.testfakestore.presentation.components.ItemRowCompose
import com.deymervilla.testfakestore.presentation.components.TopBarCompose
import com.deymervilla.testfakestore.presentation.theme.FakeStoreTheme
import com.deymervilla.testfakestore.utils.toUsd

@Composable
fun ProductScreenCompose(
    viewModel: ProductScreenViewModel = hiltViewModel(),
    attributes: ProductScreenAttributes
) {
    val uiState by viewModel.productUiState.collectAsState()
    val productUiModel by viewModel.productUiModel.collectAsState()
    viewModel.getProduct(attributes.productId)

    when(uiState) {
        is ProductUiState.Loading -> LoadingScreenCompose()
        is ProductUiState.Success -> BodyCompose(
            actions = attributes.actions,
            productModel = productUiModel,
            onClickFavorite = viewModel::setFavorite
        )
        is ProductUiState.Error -> {
            val errorMessage = (uiState as ProductUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyCompose(
    actions: ProductScreenActions,
    onClickFavorite: () -> Unit,
    productModel: ProductModel,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BannerCompose(
            imageUrl = productModel.imageUrl,
            rating = productModel.ratingLabel,
            reviewCount = productModel.ratingCount,
            isFavorite = productModel.isFavorite,
            onBackClick = { actions.onPrimaryAction.invoke() },
            onFavoriteClick = { onClickFavorite.invoke() }
        )
        TopBarCompose(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.dimen_16)),
            title = productModel.title,
            subtitle = productModel.description,
        )
        Spacer(Modifier.width(dimensionResource(R.dimen.dimen_8)))
        ItemRowCompose(
            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16)),
            title = stringResource(R.string.price),
            trailingText = productModel.rawPrice.toUsd()
        )
        Spacer(Modifier.width(dimensionResource(R.dimen.dimen_8)))
        ItemRowCompose(
            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16)),
            title = stringResource(R.string.category),
            trailingText = productModel.category
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyComposePreview() {
    val mockProduct = ProductModel(
        id = 101,
        title = "Hydrating Facial Serum",
        description = "Advanced formula with hyaluronic acid and Vitamin C.",
        category = "Skincare",
        rawPrice = 29.99,
        imageUrl = "",
        ratingCount = 850,
        ratingLabel = 4.8f,
        isFavorite = true
    )
    FakeStoreTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            BodyCompose(
                actions = ProductScreenActions(onPrimaryAction = {}),
                onClickFavorite = { println("Favorite toggled") },
                productModel = mockProduct
            )
        }
    }
}