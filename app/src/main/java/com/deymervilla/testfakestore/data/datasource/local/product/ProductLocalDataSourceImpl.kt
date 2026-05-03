package com.deymervilla.testfakestore.data.datasource.local.product

import com.deymervilla.testfakestore.data.database.dao.ProductDao
import com.deymervilla.testfakestore.data.database.entities.ProductEntity
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
): ProductLocalDataSource {

    override suspend fun fetchById(productId: Int) =
        productDao.fetchById(productId)

    override suspend fun fetchByTitle(title: String) =
        productDao.fetchByTitle(title)

    override suspend fun fetch() =
        productDao.fetchAll()

    override suspend fun fetchFavorites() =
        productDao.fetchFavorites()

    override suspend fun insert(products: List<ProductEntity>): Boolean {
        val result = productDao.insert(products)
        return result.isNotEmpty() && result.all { it > 0 }
    }

    override suspend fun update(product: ProductEntity) =
        productDao.update(product) == 1

    override suspend fun delete(product: ProductEntity) =
        productDao.delete(product) == 1

    override suspend fun delete() =
        productDao.delete() == 1
}