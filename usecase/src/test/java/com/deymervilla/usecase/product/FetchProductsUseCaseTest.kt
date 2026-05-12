package com.deymervilla.usecase.product

import com.deymervilla.repository.models.ProductModel
import com.deymervilla.repository.repositories.product.ProductRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@Suppress("UnusedFlow")
@ExperimentalCoroutinesApi
class FetchProductsUseCaseTest {

    @Mock
    private lateinit var mockRepository: ProductRepository

    private lateinit var fetchProductsUseCase: FetchProductsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchProductsUseCase = FetchProductsUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success when repository returns product list`() = runTest {
        val expectedProducts = listOf(ProductModel(id = 1, title = "Product 1"))
        `when`(mockRepository.fetch()).thenReturn(flowOf(Result.success(expectedProducts)))
        val results = fetchProductsUseCase().toList()
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedProducts, results.first().getOrNull())
        verify(mockRepository).fetch()
    }

    @Test
    fun `invoke should emit failure when repository fails to fetch products`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockRepository.fetch()).thenReturn(flowOf(Result.failure(exception)))
        val results = fetchProductsUseCase().toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).fetch()
    }
}