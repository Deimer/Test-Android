package com.deymervilla.testfakestore.features.home

data class HomeScreenActions(
    val onPrimaryAction: () -> Unit,
    val onSecondaryAction: (productId: Int) -> Unit,
    val onTertiaryAction: () -> Unit,
)