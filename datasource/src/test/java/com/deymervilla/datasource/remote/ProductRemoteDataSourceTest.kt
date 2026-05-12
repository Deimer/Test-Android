package com.deymervilla.datasource.remote

import com.deymervilla.datasource.dummyProductDTO
import com.deymervilla.datasource.remote.product.ProductRemoteDataSource
import com.deymervilla.datasource.remote.product.ProductRemoteDataSourceImpl
import com.deymervilla.network.api.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ProductRemoteDataSourceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var productRemoteDataSource: ProductRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productRemoteDataSource = ProductRemoteDataSourceImpl(mockApiService)
    }

    @Test
    fun `getProducts should return product list from API`() = runTest {
        val expectedProducts = listOf(dummyProductDTO(1, "Product 1"), dummyProductDTO(2, "Product 2"))
        `when`(mockApiService.getProducts()).thenReturn(expectedProducts)
        val result = productRemoteDataSource.getProducts()
        assertEquals(expectedProducts.size, result.size)
        assertEquals(expectedProducts[0].id, result[0].id)
        assertEquals(expectedProducts[0].title, result[0].title)
        verify(mockApiService).getProducts()
    }

    @Test
    fun `getProducts should return empty list when API returns no products`() = runTest {
        `when`(mockApiService.getProducts()).thenReturn(emptyList())
        val result = productRemoteDataSource.getProducts()
        assertTrue(result.isEmpty())
        verify(mockApiService).getProducts()
    }

    @Test
    fun `getProducts should throw exception when API fails`() = runTest {
        val expectedException = RuntimeException("API Error")
        `when`(mockApiService.getProducts()).thenThrow(expectedException)
        try {
            productRemoteDataSource.getProducts()
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals(expectedException.message, e.message)
        }
    }

    @Test
    fun `getProductById should return specific product from API`() = runTest {
        val expectedProduct = dummyProductDTO(1, "Product 1")
        `when`(mockApiService.getProductById(1)).thenReturn(expectedProduct)
        val result = productRemoteDataSource.getProductById(1)
        assertNotNull(result)
        assertEquals(expectedProduct.id, result?.id)
        assertEquals(expectedProduct.title, result?.title)
        verify(mockApiService).getProductById(1)
    }

    @Test
    fun `getProductById should return null when product is not found`() = runTest {
        `when`(mockApiService.getProductById(999)).thenReturn(null)
        val result = productRemoteDataSource.getProductById(999)
        assertNull(result)
        verify(mockApiService).getProductById(999)
    }

    @Test
    fun `getProductById should throw exception when API fails`() = runTest {
        val expectedException = RuntimeException("API Error")
        `when`(mockApiService.getProductById(1)).thenThrow(expectedException)
        try {
            productRemoteDataSource.getProductById(1)
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals(expectedException.message, e.message)
        }
    }
}