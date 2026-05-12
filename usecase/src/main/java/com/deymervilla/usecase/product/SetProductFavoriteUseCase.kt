package com.deymervilla.usecase.product

import com.deymervilla.repository.repositories.product.ProductRepository
import javax.inject.Inject

class SetProductFavoriteUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(
        productId: Int,
        isFavorite: Boolean
    ) = productRepository.setFavorite(
        productId,
        isFavorite
    )
}