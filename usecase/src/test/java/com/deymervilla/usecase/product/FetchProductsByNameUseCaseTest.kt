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
class FetchProductsByNameUseCaseTest {

    @Mock
    private lateinit var mockRepository: ProductRepository

    private lateinit var fetchProductsByNameUseCase: FetchProductsByNameUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchProductsByNameUseCase = FetchProductsByNameUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success when repository returns products by name`() = runTest {
        val expectedProducts = listOf(ProductModel(id = 1, title = "Dummy Product"))
        `when`(mockRepository.fetchByName("Dummy")).thenReturn(flowOf(Result.success(expectedProducts)))
        val results = fetchProductsByNameUseCase("Dummy").toList()
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedProducts, results.first().getOrNull())
        verify(mockRepository).fetchByName("Dummy")
    }

    @Test
    fun `invoke should emit success with empty list when no products match name`() = runTest {
        `when`(mockRepository.fetchByName("Unknown")).thenReturn(flowOf(Result.success(emptyList())))
        val results = fetchProductsByNameUseCase("Unknown").toList()
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull()?.isEmpty() == true)
        verify(mockRepository).fetchByName("Unknown")
    }

    @Test
    fun `invoke should emit failure when repository fails to fetch products by name`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockRepository.fetchByName("Dummy")).thenReturn(flowOf(Result.failure(exception)))
        val results = fetchProductsByNameUseCase("Dummy").toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).fetchByName("Dummy")
    }
}