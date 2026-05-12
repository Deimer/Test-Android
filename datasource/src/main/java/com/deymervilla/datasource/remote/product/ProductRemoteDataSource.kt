package com.deymervilla.datasource.remote.product

import com.deymervilla.network.dto.ProductDTO

interface ProductRemoteDataSource {

    suspend fun getProducts(): List<ProductDTO>

    suspend fun getProductById(
        productId: Int
    ): ProductDTO?
}