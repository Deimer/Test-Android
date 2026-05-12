package com.deymervilla.repository.repositories.product

import com.deymervilla.repository.models.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun fetch(): Flow<Result<List<ProductModel>>>

    fun fetchById(
        productId: Int
    ): Flow<Result<ProductModel>>

    fun fetchByName(
        name: String
    ): Flow<Result<List<ProductModel>>>

    fun setFavorite(
        productId: Int,
        isFavorite: Boolean
    ): Flow<Result<Boolean>>

    fun fetchFavorites(): Flow<List<ProductModel>>
}