package com.deymervilla.usecase.product

import com.deymervilla.repository.repositories.product.ProductRepository
import javax.inject.Inject

class FetchFavoriteProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke() =
        productRepository.fetchFavorites()
}