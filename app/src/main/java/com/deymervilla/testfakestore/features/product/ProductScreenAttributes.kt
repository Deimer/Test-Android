package com.deymervilla.testfakestore.features.product

import androidx.compose.material3.SnackbarHostState

data class ProductScreenAttributes(
    val productId: Int,
    val actions: ProductScreenActions,
    val snackbarHostState: SnackbarHostState
)