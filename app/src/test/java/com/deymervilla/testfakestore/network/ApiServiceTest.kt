package com.deymervilla.testfakestore.network

import com.deymervilla.testfakestore.data.network.api.ApiService
import com.deymervilla.testfakestore.dummyProductDTO
import com.deymervilla.testfakestore.dummyUserDTO
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ApiServiceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getProducts returns product list`() = runTest {
        val expectedProducts = listOf(dummyProductDTO(1, "Product 1"), dummyProductDTO(2, "Product 2"))
        `when`(mockApiService.getProducts()).thenReturn(expectedProducts)
        val response = mockApiService.getProducts()
        assertEquals(2, response.size)
        assertEquals("Product 1", response[0].title)
        assertEquals("Product 2", response[1].title)
    }

    @Test
    fun `getProducts returns empty list`() = runTest {
        `when`(mockApiService.getProducts()).thenReturn(emptyList())
        val response = mockApiService.getProducts()
        assertTrue(response.isEmpty())
    }

    @Test
    fun `getProducts handles API error`() = runTest {
        `when`(mockApiService.getProducts()).thenThrow(RuntimeException("404 Not Found"))
        try {
            mockApiService.getProducts()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("404 Not Found", e.message)
        }
    }

    @Test
    fun `getProductById returns specific product`() = runTest {
        val expectedProduct = dummyProductDTO(10, "Target Product")
        `when`(mockApiService.getProductById(10)).thenReturn(expectedProduct)
        val response = mockApiService.getProductById(10)
        assertEquals(10, response.id)
        assertEquals("Target Product", response.title)
    }

    @Test
    fun `getProductById handles API error`() = runTest {
        `when`(mockApiService.getProductById(10)).thenThrow(RuntimeException("404 Not Found"))
        try {
            mockApiService.getProductById(10)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("404 Not Found", e.message)
        }
    }

    @Test
    fun `getUserById returns specific user`() = runTest {
        val expectedUser = dummyUserDTO(1, "johndoe")
        `when`(mockApiService.getUserById(1)).thenReturn(expectedUser)
        val response = mockApiService.getUserById(1)
        assertEquals(1, response.id)
        assertEquals("johndoe", response.username)
    }

    @Test
    fun `getUserById handles API error`() = runTest {
        `when`(mockApiService.getUserById(1)).thenThrow(RuntimeException("404 Not Found"))
        try {
            mockApiService.getUserById(1)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("404 Not Found", e.message)
        }
    }
}