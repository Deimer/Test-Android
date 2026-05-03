package com.deymervilla.testfakestore.domain.repositories.product

import com.deymervilla.testfakestore.data.datasource.local.product.ProductLocalDataSource
import com.deymervilla.testfakestore.data.datasource.remote.product.ProductRemoteDataSource
import com.deymervilla.testfakestore.domain.mappers.toEntity
import com.deymervilla.testfakestore.domain.mappers.toModel
import com.deymervilla.testfakestore.domain.models.ProductModel
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
): ProductRepository {

    override fun fetch() = flow {
        try {
            val products = productLocalDataSource.fetch().map { it.toModel() }.ifEmpty {
                val newProducts = productRemoteDataSource.getProducts()
                productLocalDataSource.insert(newProducts.map { it.toEntity() })
                productLocalDataSource.fetch().map { it.toModel() }
            }
            emit(value = Result.success(products))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun fetchById(productId: Int) = flow {
        val result: Result<ProductModel> = try {
            productLocalDataSource.fetchById(productId)?.toModel()?.let { character ->
                Result.success(character)
            } ?: run {
                productRemoteDataSource.getProductById(productId)?.toModel()?.let { character ->
                    Result.success(character)
                } ?: Result.failure(NoSuchElementException(""))
            }
        } catch (ioException: IOException) {
            Result.failure(ioException)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
        emit(result)
    }

    override fun fetchByName(name: String) = flow {
        try {
            val products = productLocalDataSource.fetchByTitle(name).map { it.toModel() }
            emit(value = Result.success(products))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun setFavorite(
        productId: Int,
        isFavorite: Boolean
    ) = flow {
        productLocalDataSource.fetchById(productId)?.let { character ->
            productLocalDataSource.update(character.copy(isFavorite = isFavorite))
            emit(value = Result.success(true))
        } ?: run {
            emit(value = Result.success(false))
        }
    }

    override fun fetchFavorites() = flow {
        try {
            val products = productLocalDataSource.fetchFavorites().map { it.toModel() }
            emit(value = Result.success(products))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }
}