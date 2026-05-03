package com.deymervilla.testfakestore.domain.models

data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rawPrice: Double,
    val ratingCount: Int,
    val ratingLabel: Float
)