package com.deymervilla.testfakestore.domain.mappers

import com.deymervilla.testfakestore.data.database.entities.ProductEntity
import com.deymervilla.testfakestore.data.network.dto.ProductDTO
import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.domain.utils.orZero

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
        ratingCount = this.rate.toInt(),
        ratingLabel = this.count.toFloat()
    )
}