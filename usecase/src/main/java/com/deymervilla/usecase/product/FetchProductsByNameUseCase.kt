package com.deymervilla.usecase.product

import com.deymervilla.repository.repositories.product.ProductRepository
import javax.inject.Inject

class FetchProductsByNameUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(name: String) =
        productRepository.fetchByName(name)
}