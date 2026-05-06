package com.deymervilla.testfakestore.usecase.product

import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import com.deymervilla.testfakestore.domain.usecases.product.FetchFavoriteProductsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@Suppress("UnusedFlow")
@ExperimentalCoroutinesApi
class FetchFavoriteProductsUseCaseTest {

    @Mock
    private lateinit var mockRepository: ProductRepository

    private lateinit var fetchFavoriteProductsUseCase: FetchFavoriteProductsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchFavoriteProductsUseCase = FetchFavoriteProductsUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit list of favorite products`() = runTest {
        val expectedProducts = listOf(
            ProductModel(
                id = 1,
                title = "Favorite Product",
                isFavorite = true
            )
        )
        `when`(mockRepository.fetchFavorites()).thenReturn(flowOf(expectedProducts))
        val result = fetchFavoriteProductsUseCase().first()
        assertEquals(1, result.size)
        assertEquals("Favorite Product", result.first().title)
        assertTrue(result.first().isFavorite)
        verify(mockRepository).fetchFavorites()
    }

    @Test
    fun `invoke should emit empty list when no favorites exist`() = runTest {
        `when`(mockRepository.fetchFavorites()).thenReturn(flowOf(emptyList()))
        val result = fetchFavoriteProductsUseCase().first()
        assertTrue(result.isEmpty())
        verify(mockRepository).fetchFavorites()
    }
}