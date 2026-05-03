package com.deymervilla.testfakestore.domain.usecases.product

import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import javax.inject.Inject

class FetchProductsByNameUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(name: String) =
        productRepository.fetchByName(name)
}