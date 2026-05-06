package com.deymervilla.testfakestore.domain.models

data class ProductModel(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val rawPrice: Double = 0.0,
    val ratingCount: Int = 0,
    val ratingLabel: Float = 0.0f,
    val isFavorite: Boolean = false
)