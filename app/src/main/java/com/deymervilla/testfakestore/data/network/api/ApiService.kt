package com.deymervilla.testfakestore.data.network.api

import com.deymervilla.testfakestore.data.network.constants.NetworkConstants.PARAMETERS.PARAMETER_PRODUCT
import com.deymervilla.testfakestore.data.network.constants.NetworkConstants.PARAMETERS.PARAMETER_USER
import com.deymervilla.testfakestore.data.network.constants.NetworkConstants.URLs.PRODUCTS_PATH
import com.deymervilla.testfakestore.data.network.constants.NetworkConstants.URLs.PRODUCT_PATH
import com.deymervilla.testfakestore.data.network.constants.NetworkConstants.URLs.USER_PATH
import com.deymervilla.testfakestore.data.network.dto.ProductDTO
import com.deymervilla.testfakestore.data.network.dto.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(PRODUCTS_PATH)
    suspend fun getProducts(): List<ProductDTO>

    @GET(PRODUCT_PATH)
    suspend fun getProductById(
        @Path(PARAMETER_PRODUCT) productId: Int
    ): ProductDTO

    @GET(USER_PATH)
    suspend fun getUserById(
        @Path(PARAMETER_USER) userId: Int
    ): UserDTO
}