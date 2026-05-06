package com.deymervilla.testfakestore

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deymervilla.testfakestore.data.database.RoomDatabase
import com.deymervilla.testfakestore.data.database.dao.ProductDao
import com.deymervilla.testfakestore.data.database.dao.UserDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RoomDaoTest {

    private lateinit var database: RoomDatabase
    private lateinit var userDao: UserDao
    private lateinit var productDao: ProductDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = database.getUserDao()
        productDao = database.getProductDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    // ───── User ─────

    @Test
    fun insertAndFetchUsers() = runTest {
        val users = listOf(dummyUserEntity(1, "johndoe"), dummyUserEntity(2, "janedoe"))
        userDao.insert(users)
        val result = userDao.fetchAll()
        assertEquals(2, result.size)
        assertEquals("johndoe", result[0].username)
    }

    @Test
    fun fetchUserById() = runTest {
        val user = dummyUserEntity(10, "targetuser")
        userDao.insert(listOf(user))
        val result = userDao.fetchById(10)
        assertNotNull(result)
        assertEquals("targetuser", result?.username)
    }

    @Test
    fun fetchUserById_returnsNull_whenNotFound() = runTest {
        val result = userDao.fetchById(999)
        assertNull(result)
    }

    @Test
    fun fetchUserByUsername() = runTest {
        val users = listOf(dummyUserEntity(1, "johndoe"), dummyUserEntity(2, "janedoe"))
        userDao.insert(users)
        val result = userDao.fetchByUsername("johndoe")
        assertNotNull(result)
        assertEquals("johndoe", result?.username)
    }

    @Test
    fun fetchUserByUsername_returnsNull_whenNotFound() = runTest {
        val result = userDao.fetchByUsername("unknown")
        assertNull(result)
    }

    @Test
    fun fetchUserByEmail() = runTest {
        val user = dummyUserEntity(1, "johndoe", email = "john@dummy.com")
        userDao.insert(listOf(user))
        val result = userDao.fetchByEmail("john@dummy.com")
        assertNotNull(result)
        assertEquals("john@dummy.com", result?.email)
    }

    @Test
    fun fetchUserByEmail_returnsNull_whenNotFound() = runTest {
        val result = userDao.fetchByEmail("unknown@dummy.com")
        assertNull(result)
    }

    @Test
    fun updateUser() = runTest {
        val user = dummyUserEntity(1, "johndoe")
        userDao.insert(listOf(user))
        val updatedUser = user.copy(username = "johndoe_updated")
        userDao.update(updatedUser)
        val result = userDao.fetchById(1)
        assertEquals("johndoe_updated", result?.username)
    }

    @Test
    fun deleteUser() = runTest {
        val user = dummyUserEntity(1, "johndoe")
        userDao.insert(listOf(user))
        userDao.delete(user)
        val result = userDao.fetchAll()
        assertTrue(result.isEmpty())
    }

    @Test
    fun clearAllUsers() = runTest {
        userDao.insert(listOf(dummyUserEntity(1, "johndoe"), dummyUserEntity(2, "janedoe")))
        userDao.delete()
        val result = userDao.fetchAll()
        assertTrue(result.isEmpty())
    }

    // ───── Product ─────

    @Test
    fun insertAndFetchProducts() = runTest {
        val products = listOf(dummyProductEntity(1, "Product 1"), dummyProductEntity(2, "Product 2"))
        productDao.insert(products)
        val result = productDao.fetchAll()
        assertEquals(2, result.size)
        assertEquals("Product 1", result[0].title)
    }

    @Test
    fun fetchProductById() = runTest {
        val product = dummyProductEntity(10, "Target Product")
        productDao.insert(listOf(product))
        val result = productDao.fetchById(10)
        assertNotNull(result)
        assertEquals("Target Product", result?.title)
    }

    @Test
    fun fetchProductById_returnsNull_whenNotFound() = runTest {
        val result = productDao.fetchById(999)
        assertNull(result)
    }

    @Test
    fun fetchProductByTitle() = runTest {
        val products = listOf(dummyProductEntity(1, "Dummy Product"), dummyProductEntity(2, "Other"))
        productDao.insert(products)
        val result = productDao.fetchByTitle("%Dummy%")
        assertEquals(1, result.size)
        assertEquals("Dummy Product", result[0].title)
    }

    @Test
    fun fetchProductByTitle_returnsEmpty_whenNoMatch() = runTest {
        productDao.insert(listOf(dummyProductEntity(1, "Dummy Product")))
        val result = productDao.fetchByTitle("%Unknown%")
        assertTrue(result.isEmpty())
    }

    @Test
    fun fetchFavoriteProducts() = runTest {
        val products = listOf(
            dummyProductEntity(1, "Favorite", isFavorite = true),
            dummyProductEntity(2, "Not Favorite", isFavorite = false)
        )
        productDao.insert(products)
        val result = productDao.fetchFavorites().first()
        assertEquals(1, result.size)
        assertEquals("Favorite", result[0].title)
        assertTrue(result[0].isFavorite)
    }

    @Test
    fun fetchFavoriteProducts_returnsEmpty_whenNoFavorites() = runTest {
        productDao.insert(listOf(dummyProductEntity(1, "Not Favorite", isFavorite = false)))
        val result = productDao.fetchFavorites().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun updateProduct() = runTest {
        val product = dummyProductEntity(1, "Product 1")
        productDao.insert(listOf(product))
        val updatedProduct = product.copy(title = "Product 1 Updated")
        productDao.update(updatedProduct)
        val result = productDao.fetchById(1)
        assertEquals("Product 1 Updated", result?.title)
    }

    @Test
    fun deleteProduct() = runTest {
        val product = dummyProductEntity(1, "Product 1")
        productDao.insert(listOf(product))
        productDao.delete(product)
        val result = productDao.fetchAll()
        assertTrue(result.isEmpty())
    }

    @Test
    fun clearAllProducts() = runTest {
        productDao.insert(listOf(dummyProductEntity(1, "Product 1"), dummyProductEntity(2, "Product 2")))
        productDao.delete()
        val result = productDao.fetchAll()
        assertTrue(result.isEmpty())
    }

    @Test
    fun insertProduct_replacesOnConflict() = runTest {
        val product = dummyProductEntity(1, "Original")
        productDao.insert(listOf(product))
        val duplicate = product.copy(title = "Replaced")
        productDao.insert(listOf(duplicate))
        val result = productDao.fetchAll()
        assertEquals(1, result.size)
        assertEquals("Replaced", result[0].title)
    }
}