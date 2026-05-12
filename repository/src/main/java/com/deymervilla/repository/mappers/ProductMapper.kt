package com.deymervilla.repository.mappers

import com.deymervilla.database.entities.ProductEntity
import com.deymervilla.network.dto.ProductDTO
import com.deymervilla.repository.models.ProductModel
import com.deymervilla.repository.utils.orZero

fun ProductDTO.toModel() = ProductModel(
    id = id.orZero(),
    title = title.orEmpty(),
    rawPrice = price.orZero(),
    description = description.orEmpty(),
    category = category.orEmpty(),
    imageUrl = image.orEmpty(),
    ratingCount = rating?.count.orZero(),
    ratingLabel = rating?.rate?.toFloat().orZero()
)

fun ProductDTO.toEntity(): ProductEntity {
    return ProductEntity(
        id = id.orZero(),
        title = title.orEmpty(),
        price = price.orZero(),
        description = description.orEmpty(),
        category = category.orEmpty(),
        image = image.orEmpty(),
        rate = rating?.rate.orZero(),
        count = rating?.count.orZero(),
        isFavorite = false
    )
}

fun ProductEntity.toModel(): ProductModel {
    return ProductModel(
        id = this.id,
        title = this.title,
        rawPrice = this.price,
        description = this.description,
        category = this.category,
        imageUrl = this.image,
        ratingCount = this.count,
        ratingLabel = this.rate.toFloat(),
        isFavorite = this.isFavorite
    )
}