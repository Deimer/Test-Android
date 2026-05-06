package com.deymervilla.testfakestore.datasource.local

import com.deymervilla.testfakestore.data.database.dao.UserDao
import com.deymervilla.testfakestore.data.datasource.local.user.UserLocalDataSource
import com.deymervilla.testfakestore.data.datasource.local.user.UserLocalDataSourceImpl
import com.deymervilla.testfakestore.dummyUserEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UserLocalDataSourceTest {

    @Mock
    private lateinit var mockUserDao: UserDao

    private lateinit var userLocalDataSource: UserLocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userLocalDataSource = UserLocalDataSourceImpl(mockUserDao)
    }

    @Test
    fun `insert should return true when insertion is successful`() = runTest {
        val users = listOf(dummyUserEntity(1, "johndoe"))
        `when`(mockUserDao.insert(users)).thenReturn(listOf(1L))
        val result = userLocalDataSource.insert(users)
        assertTrue(result)
        verify(mockUserDao).insert(users)
    }

    @Test
    fun `insert should return false when insertion fails`() = runTest {
        val users = listOf(dummyUserEntity(1, "johndoe"))
        `when`(mockUserDao.insert(users)).thenReturn(emptyList())
        val result = userLocalDataSource.insert(users)
        assertFalse(result)
        verify(mockUserDao).insert(users)
    }

    @Test
    fun `update should return true when update is successful`() = runTest {
        val user = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.update(user)).thenReturn(1)
        val result = userLocalDataSource.update(user)
        assertTrue(result)
        verify(mockUserDao).update(user)
    }

    @Test
    fun `update should return false when update fails`() = runTest {
        val user = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.update(user)).thenReturn(0)
        val result = userLocalDataSource.update(user)
        assertFalse(result)
        verify(mockUserDao).update(user)
    }

    @Test
    fun `fetch should return user list when it exists`() = runTest {
        val expectedUsers = listOf(dummyUserEntity(1, "johndoe"))
        `when`(mockUserDao.fetchAll()).thenReturn(expectedUsers)
        val result = userLocalDataSource.fetch()
        assertEquals(expectedUsers, result)
        verify(mockUserDao).fetchAll()
    }

    @Test
    fun `fetch should return empty list when there are no users`() = runTest {
        `when`(mockUserDao.fetchAll()).thenReturn(emptyList())
        val result = userLocalDataSource.fetch()
        assertTrue(result.isEmpty())
        verify(mockUserDao).fetchAll()
    }

    @Test
    fun `fetchById should return user when it exists`() = runTest {
        val expectedUser = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.fetchById(1)).thenReturn(expectedUser)
        val result = userLocalDataSource.fetchById(1)
        assertEquals(expectedUser, result)
        verify(mockUserDao).fetchById(1)
    }

    @Test
    fun `fetchById should return null when user does not exist`() = runTest {
        `when`(mockUserDao.fetchById(999)).thenReturn(null)
        val result = userLocalDataSource.fetchById(999)
        assertNull(result)
        verify(mockUserDao).fetchById(999)
    }

    @Test
    fun `fetchByUsername should return user when it exists`() = runTest {
        val expectedUser = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.fetchByUsername("johndoe")).thenReturn(expectedUser)
        val result = userLocalDataSource.fetchByUsername("johndoe")
        assertEquals(expectedUser, result)
        verify(mockUserDao).fetchByUsername("johndoe")
    }

    @Test
    fun `fetchByUsername should return null when user does not exist`() = runTest {
        `when`(mockUserDao.fetchByUsername("unknown")).thenReturn(null)
        val result = userLocalDataSource.fetchByUsername("unknown")
        assertNull(result)
        verify(mockUserDao).fetchByUsername("unknown")
    }

    @Test
    fun `fetchByEmail should return user when it exists`() = runTest {
        val expectedUser = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.fetchByEmail("john@dummy.com")).thenReturn(expectedUser)
        val result = userLocalDataSource.fetchByEmail("john@dummy.com")
        assertEquals(expectedUser, result)
        verify(mockUserDao).fetchByEmail("john@dummy.com")
    }

    @Test
    fun `fetchByEmail should return null when user does not exist`() = runTest {
        `when`(mockUserDao.fetchByEmail("unknown@dummy.com")).thenReturn(null)
        val result = userLocalDataSource.fetchByEmail("unknown@dummy.com")
        assertNull(result)
        verify(mockUserDao).fetchByEmail("unknown@dummy.com")
    }

    @Test
    fun `delete should return true when deletion is successful`() = runTest {
        val user = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.delete(user)).thenReturn(1)
        val result = userLocalDataSource.delete(user)
        assertTrue(result)
        verify(mockUserDao).delete(user)
    }

    @Test
    fun `delete should return false when deletion fails`() = runTest {
        val user = dummyUserEntity(1, "johndoe")
        `when`(mockUserDao.delete(user)).thenReturn(0)
        val result = userLocalDataSource.delete(user)
        assertFalse(result)
        verify(mockUserDao).delete(user)
    }
}