package com.deymervilla.testfakestore.data.datasource.local.product

import com.deymervilla.testfakestore.data.database.entities.ProductEntity

interface ProductLocalDataSource {

    suspend fun fetchById(
        productId: Int
    ): ProductEntity?

    suspend fun fetchByTitle(
        title: String
    ): List<ProductEntity>

    suspend fun fetchAll(): List<ProductEntity>

    suspend fun fetchFavorites(): List<ProductEntity>

    suspend fun insert(products: List<ProductEntity>): Boolean

    suspend fun update(product: ProductEntity): Boolean

    suspend fun delete(product: ProductEntity): Boolean

    suspend fun deleteAll(): Boolean
}