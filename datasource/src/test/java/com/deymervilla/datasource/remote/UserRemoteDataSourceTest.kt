package com.deymervilla.datasource.remote

import com.deymervilla.datasource.dummyUserDTO
import com.deymervilla.datasource.remote.user.UserRemoteDataSource
import com.deymervilla.datasource.remote.user.UserRemoteDataSourceImpl
import com.deymervilla.network.api.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
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
class UserRemoteDataSourceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRemoteDataSource = UserRemoteDataSourceImpl(mockApiService)
    }

    @Test
    fun `getUserById should return specific user from API`() = runTest {
        val expectedUser = dummyUserDTO(1, "johndoe")
        `when`(mockApiService.getUserById(1)).thenReturn(expectedUser)
        val result = userRemoteDataSource.getUserById(1)
        assertNotNull(result)
        assertEquals(expectedUser.id, result?.id)
        assertEquals(expectedUser.username, result?.username)
        verify(mockApiService).getUserById(1)
    }

    @Test
    fun `getUserById should return null when user is not found`() = runTest {
        `when`(mockApiService.getUserById(999)).thenReturn(null)
        val result = userRemoteDataSource.getUserById(999)
        assertNull(result)
        verify(mockApiService).getUserById(999)
    }

    @Test
    fun `getUserById should throw exception when API fails`() = runTest {
        val expectedException = RuntimeException("API Error")
        `when`(mockApiService.getUserById(1)).thenThrow(expectedException)
        try {
            userRemoteDataSource.getUserById(1)
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals(expectedException.message, e.message)
        }
    }
}