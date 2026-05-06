package com.deymervilla.testfakestore.usecase.product

import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import com.deymervilla.testfakestore.domain.usecases.product.FetchProductByIdUseCase
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
class FetchProductByIdUseCaseTest {

    @Mock
    private lateinit var mockRepository: ProductRepository

    private lateinit var fetchProductByIdUseCase: FetchProductByIdUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchProductByIdUseCase = FetchProductByIdUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success when repository returns product by id`() = runTest {
        val expectedProduct = ProductModel(id = 1, title = "Product 1")
        `when`(mockRepository.fetchById(1)).thenReturn(flowOf(Result.success(expectedProduct)))

        val results = fetchProductByIdUseCase(1).toList()

        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedProduct, results.first().getOrNull())
        verify(mockRepository).fetchById(1)
    }

    @Test
    fun `invoke should emit failure when repository fails to fetch product by id`() = runTest {
        val exception = RuntimeException("Product not found")
        `when`(mockRepository.fetchById(999)).thenReturn(flowOf(Result.failure(exception)))

        val results = fetchProductByIdUseCase(999).toList()

        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).fetchById(999)
    }
}