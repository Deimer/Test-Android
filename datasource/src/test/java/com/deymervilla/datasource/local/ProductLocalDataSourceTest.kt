package com.deymervilla.datasource.local

import com.deymervilla.database.dao.ProductDao
import com.deymervilla.datasource.dummyProductEntity
import com.deymervilla.datasource.local.product.ProductLocalDataSource
import com.deymervilla.datasource.local.product.ProductLocalDataSourceImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
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

@ExperimentalCoroutinesApi
class ProductLocalDataSourceTest {

    @Mock
    private lateinit var mockProductDao: ProductDao

    private lateinit var productLocalDataSource: ProductLocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productLocalDataSource = ProductLocalDataSourceImpl(mockProductDao)
    }

    @Test
    fun `insert should return true when insertion is successful`() = runTest {
        val products = listOf(dummyProductEntity(1, "Product 1"))
        `when`(mockProductDao.insert(products)).thenReturn(listOf(1L))
        val result = productLocalDataSource.insert(products)
        assertTrue(result)
        verify(mockProductDao).insert(products)
    }

    @Test
    fun `insert should return false when insertion fails`() = runTest {
        val products = listOf(dummyProductEntity(1, "Product 1"))
        `when`(mockProductDao.insert(products)).thenReturn(emptyList())
        val result = productLocalDataSource.insert(products)
        assertFalse(result)
        verify(mockProductDao).insert(products)
    }

    @Test
    fun `update should return true when update is successful`() = runTest {
        val product = dummyProductEntity(1, "Product 1")
        `when`(mockProductDao.update(product)).thenReturn(1)
        val result = productLocalDataSource.update(product)
        assertTrue(result)
        verify(mockProductDao).update(product)
    }

    @Test
    fun `update should return false when update fails`() = runTest {
        val product = dummyProductEntity(1, "Product 1")
        `when`(mockProductDao.update(product)).thenReturn(0)
        val result = productLocalDataSource.update(product)
        assertFalse(result)
        verify(mockProductDao).update(product)
    }

    @Test
    fun `fetch should return product list when it exists`() = runTest {
        val expectedProducts = listOf(dummyProductEntity(1, "Product 1"))
        `when`(mockProductDao.fetchAll()).thenReturn(expectedProducts)
        val result = productLocalDataSource.fetch()
        assertEquals(expectedProducts, result)
        verify(mockProductDao).fetchAll()
    }

    @Test
    fun `fetch should return empty list when there are no products`() = runTest {
        `when`(mockProductDao.fetchAll()).thenReturn(emptyList())
        val result = productLocalDataSource.fetch()
        assertTrue(result.isEmpty())
        verify(mockProductDao).fetchAll()
    }

    @Test
    fun `fetchById should return product when it exists`() = runTest {
        val expectedProduct = dummyProductEntity(1, "Product 1")
        `when`(mockProductDao.fetchById(1)).thenReturn(expectedProduct)
        val result = productLocalDataSource.fetchById(1)
        assertEquals(expectedProduct, result)
        verify(mockProductDao).fetchById(1)
    }

    @Test
    fun `fetchById should return null when product does not exist`() = runTest {
        `when`(mockProductDao.fetchById(999)).thenReturn(null)
        val result = productLocalDataSource.fetchById(999)
        assertNull(result)
        verify(mockProductDao).fetchById(999)
    }

    @Test
    fun `fetchByTitle should return matching products`() = runTest {
        val expectedProducts = listOf(dummyProductEntity(1, "Dummy Product"))
        `when`(mockProductDao.fetchByTitle("Dummy")).thenReturn(expectedProducts)
        val result = productLocalDataSource.fetchByTitle("Dummy")
        assertEquals(expectedProducts.size, result.size)
        assertEquals(expectedProducts[0].title, result[0].title)
        verify(mockProductDao).fetchByTitle("Dummy")
    }

    @Test
    fun `fetchByTitle should return empty list when no matches found`() = runTest {
        `when`(mockProductDao.fetchByTitle("Unknown")).thenReturn(emptyList())
        val result = productLocalDataSource.fetchByTitle("Unknown")
        assertTrue(result.isEmpty())
        verify(mockProductDao).fetchByTitle("Unknown")
    }

    @Test
    fun `fetchFavorites should return flow of favorite products`() = runTest {
        val favoriteProducts = listOf(dummyProductEntity(1, "Favorite Product", isFavorite = true))
        val expectedFlow = flowOf(favoriteProducts)
        `when`(mockProductDao.fetchFavorites()).thenReturn(expectedFlow)
        val result = productLocalDataSource.fetchFavorites().first()
        assertEquals(favoriteProducts, result)
        verify(mockProductDao).fetchFavorites()
    }

    @Test
    fun `delete product should return true when deletion is successful`() = runTest {
        val product = dummyProductEntity(1, "Product 1")
        `when`(mockProductDao.delete(product)).thenReturn(1)
        val result = productLocalDataSource.delete(product)
        assertTrue(result)
        verify(mockProductDao).delete(product)
    }

    @Test
    fun `delete product should return false when deletion fails`() = runTest {
        val product = dummyProductEntity(1, "Product 1")
        `when`(mockProductDao.delete(product)).thenReturn(0)
        val result = productLocalDataSource.delete(product)
        assertFalse(result)
        verify(mockProductDao).delete(product)
    }

    @Test
    fun `delete all should return true when deletion is successful`() = runTest {
        `when`(mockProductDao.delete()).thenReturn(1)
        val result = productLocalDataSource.delete()
        assertTrue(result)
        verify(mockProductDao).delete()
    }

    @Test
    fun `delete all should return false when deletion fails`() = runTest {
        `when`(mockProductDao.delete()).thenReturn(0)
        val result = productLocalDataSource.delete()
        assertFalse(result)
        verify(mockProductDao).delete()
    }
}