package com.deymervilla.testfakestore.data.datasource.remote.product

import com.deymervilla.testfakestore.data.network.api.ApiService
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): ProductRemoteDataSource {

    override suspend fun getProducts() =
        apiService.getProducts()

    override suspend fun getProductById(productId: Int) =
        apiService.getProductById(productId)
}