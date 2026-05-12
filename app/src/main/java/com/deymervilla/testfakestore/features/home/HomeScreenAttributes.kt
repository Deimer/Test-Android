package com.deymervilla.testfakestore.features.home

import androidx.compose.material3.SnackbarHostState

data class HomeScreenAttributes(
    val actions: HomeScreenActions,
    val snackbarHostState: SnackbarHostState
)