package com.deymervilla.testfakestore.repository

import com.deymervilla.testfakestore.data.datasource.local.product.ProductLocalDataSource
import com.deymervilla.testfakestore.data.datasource.remote.product.ProductRemoteDataSource
import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import com.deymervilla.testfakestore.domain.repositories.product.ProductRepositoryImpl
import com.deymervilla.testfakestore.dummyProductDTO
import com.deymervilla.testfakestore.dummyProductEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import kotlin.collections.first

@ExperimentalCoroutinesApi
class ProductRepositoryTest {

    @Mock
    private lateinit var mockLocalDataSource: ProductLocalDataSource

    @Mock
    private lateinit var mockRemoteDataSource: ProductRemoteDataSource

    private lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productRepository = ProductRepositoryImpl(mockLocalDataSource, mockRemoteDataSource)
    }

    @Test
    fun `fetch emits success with local data when available`() = runTest {
        val entities = listOf(dummyProductEntity(1, "Product 1"))
        `when`(mockLocalDataSource.fetch()).thenReturn(entities)
        val results = mutableListOf<Result<List<ProductModel>>>()
        productRepository.fetch().toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(1, results.first().getOrNull()?.size)
        assertEquals("Product 1", results.first().getOrNull()?.first()?.title)
    }

    @Test
    fun `fetch fetches from remote and syncs locally when local is empty`() = runTest {
        val dtoList = listOf(dummyProductDTO(1, "Product 1"))
        `when`(mockLocalDataSource.fetch())
            .thenReturn(emptyList())
            .thenReturn(listOf(dummyProductEntity(1, "Product 1")))
        `when`(mockRemoteDataSource.getProducts()).thenReturn(dtoList)
        `when`(mockLocalDataSource.insert(any())).thenReturn(true)
        val results = mutableListOf<Result<List<ProductModel>>>()
        productRepository.fetch().toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals("Product 1", results.first().getOrNull()?.first()?.title)
        verify(mockRemoteDataSource).getProducts()
        verify(mockLocalDataSource).insert(any())
    }

    @Test
    fun `fetch emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockLocalDataSource.fetch()).thenThrow(exception)
        val results = mutableListOf<Result<List<ProductModel>>>()
        productRepository.fetch().toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }

    @Test
    fun `fetchById emits success with local product when available`() = runTest {
        val entity = dummyProductEntity(1, "Product 1")
        `when`(mockLocalDataSource.fetchById(1)).thenReturn(entity)
        val results = mutableListOf<Result<ProductModel>>()
        productRepository.fetchById(1).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals("Product 1", results.first().getOrNull()?.title)
        verify(mockLocalDataSource).fetchById(1)
        verify(mockRemoteDataSource, never()).getProductById(any())
    }

    @Test
    fun `fetchById fetches from remote when local product is not found`() = runTest {
        val dto = dummyProductDTO(1, "Product 1")
        `when`(mockLocalDataSource.fetchById(1)).thenReturn(null)
        `when`(mockRemoteDataSource.getProductById(1)).thenReturn(dto)
        val results = mutableListOf<Result<ProductModel>>()
        productRepository.fetchById(1).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals("Product 1", results.first().getOrNull()?.title)
        verify(mockRemoteDataSource).getProductById(1)
    }

    @Test
    fun `fetchById emits failure when product not found in local nor remote`() = runTest {
        `when`(mockLocalDataSource.fetchById(999)).thenReturn(null)
        `when`(mockRemoteDataSource.getProductById(999)).thenReturn(null)
        val results = mutableListOf<Result<ProductModel>>()
        productRepository.fetchById(999).toList(results)
        assertTrue(results.first().isFailure)
        assertTrue(results.first().exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun `fetchById emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockLocalDataSource.fetchById(1)).thenThrow(exception)
        val results = mutableListOf<Result<ProductModel>>()
        productRepository.fetchById(1).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }

    @Test
    fun `fetchByName emits success with local results`() = runTest {
        val entities = listOf(dummyProductEntity(1, "Dummy Product"))
        `when`(mockLocalDataSource.fetchByTitle("Dummy")).thenReturn(entities)
        val results = mutableListOf<Result<List<ProductModel>>>()
        productRepository.fetchByName("Dummy").toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(1, results.first().getOrNull()?.size)
        assertEquals("Dummy Product", results.first().getOrNull()?.first()?.title)
        verify(mockLocalDataSource).fetchByTitle("Dummy")
    }

    @Test
    fun `fetchByName emits success with empty list when no matches found`() = runTest {
        `when`(mockLocalDataSource.fetchByTitle("Unknown")).thenReturn(emptyList())
        val results = mutableListOf<Result<List<ProductModel>>>()
        productRepository.fetchByName("Unknown").toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `fetchByName emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockLocalDataSource.fetchByTitle("Dummy")).thenThrow(exception)
        val results = mutableListOf<Result<List<ProductModel>>>()
        productRepository.fetchByName("Dummy").toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }

    @Test
    fun `setFavorite emits success true when product exists`() = runTest {
        val entity = dummyProductEntity(1, "Product 1")
        `when`(mockLocalDataSource.fetchById(1)).thenReturn(entity)
        `when`(mockLocalDataSource.update(any())).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        productRepository.setFavorite(1, true).toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == true)
        verify(mockLocalDataSource).update(entity.copy(isFavorite = true))
    }

    @Test
    fun `setFavorite emits success false when product does not exist`() = runTest {
        `when`(mockLocalDataSource.fetchById(999)).thenReturn(null)
        val results = mutableListOf<Result<Boolean>>()
        productRepository.setFavorite(999, true).toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == false)
        verify(mockLocalDataSource, never()).update(any())
    }

    @Test
    fun `fetchFavorites emits list of favorite products`() = runTest {
        val entities = listOf(dummyProductEntity(1, "Favorite Product", isFavorite = true))
        `when`(mockLocalDataSource.fetchFavorites()).thenReturn(flowOf(entities))
        val result = productRepository.fetchFavorites().first()
        assertEquals(1, result.size)
        assertEquals("Favorite Product", result.first().title)
        assertTrue(result.first().isFavorite)
    }

    @Test
    fun `fetchFavorites emits empty list when no favorites exist`() = runTest {
        `when`(mockLocalDataSource.fetchFavorites()).thenReturn(flowOf(emptyList()))
        val result = productRepository.fetchFavorites().first()
        assertTrue(result.isEmpty())
    }
}