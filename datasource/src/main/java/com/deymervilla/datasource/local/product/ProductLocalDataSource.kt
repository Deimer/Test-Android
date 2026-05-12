package com.deymervilla.datasource.local.product

import com.deymervilla.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {

    suspend fun fetchById(
        productId: Int
    ): ProductEntity?

    suspend fun fetchByTitle(
        title: String
    ): List<ProductEntity>

    suspend fun fetch(): List<ProductEntity>

    fun fetchFavorites(): Flow<List<ProductEntity>>

    suspend fun insert(products: List<ProductEntity>): Boolean

    suspend fun update(product: ProductEntity): Boolean

    suspend fun delete(product: ProductEntity): Boolean

    suspend fun delete(): Boolean
}