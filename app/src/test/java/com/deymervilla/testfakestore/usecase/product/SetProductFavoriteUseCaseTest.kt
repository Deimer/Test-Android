package com.deymervilla.testfakestore.usecase.product

import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import com.deymervilla.testfakestore.domain.usecases.product.SetProductFavoriteUseCase
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
class SetProductFavoriteUseCaseTest {

    @Mock
    private lateinit var mockRepository: ProductRepository

    private lateinit var setProductFavoriteUseCase: SetProductFavoriteUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        setProductFavoriteUseCase = SetProductFavoriteUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success true when product is marked as favorite`() = runTest {
        `when`(mockRepository.setFavorite(1, true)).thenReturn(flowOf(Result.success(true)))
        val results = setProductFavoriteUseCase(1, true).toList()
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == true)
        verify(mockRepository).setFavorite(1, true)
    }

    @Test
    fun `invoke should emit success false when product is unmarked as favorite`() = runTest {
        `when`(mockRepository.setFavorite(1, false)).thenReturn(flowOf(Result.success(false)))
        val results = setProductFavoriteUseCase(1, false).toList()
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == false)
        verify(mockRepository).setFavorite(1, false)
    }

    @Test
    fun `invoke should emit failure when repository fails to set favorite`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockRepository.setFavorite(999, true)).thenReturn(flowOf(Result.failure(exception)))
        val results = setProductFavoriteUseCase(999, true).toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).setFavorite(999, true)
    }
}