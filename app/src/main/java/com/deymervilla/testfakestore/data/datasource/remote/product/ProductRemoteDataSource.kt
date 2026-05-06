package com.deymervilla.testfakestore.data.datasource.remote.product

import com.deymervilla.testfakestore.data.network.dto.ProductDTO

interface ProductRemoteDataSource {

    suspend fun getProducts(): List<ProductDTO>

    suspend fun getProductById(
        productId: Int
    ): ProductDTO?
}