package com.deymervilla.repository.repository

import com.deymervilla.datasource.local.user.UserLocalDataSource
import com.deymervilla.datasource.remote.user.UserRemoteDataSource
import com.deymervilla.repository.dummyUserDTO
import com.deymervilla.repository.dummyUserEntity
import com.deymervilla.repository.mappers.toModel
import com.deymervilla.repository.models.UserModel
import com.deymervilla.repository.repositories.user.UserRepository
import com.deymervilla.repository.repositories.user.UserRepositoryImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @Mock
    private lateinit var mockLocalDataSource: UserLocalDataSource

    @Mock
    private lateinit var mockRemoteDataSource: UserRemoteDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepositoryImpl(mockLocalDataSource, mockRemoteDataSource)
    }

    @Test
    fun `fetch emits success with local user when available`() = runTest {
        val entity = dummyUserEntity(1, "johndoe")
        `when`(mockLocalDataSource.fetchById(1)).thenReturn(entity)
        val results = mutableListOf<Result<UserModel>>()
        userRepository.fetch(1).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals("johndoe", results.first().getOrNull()?.username)
        verify(mockLocalDataSource).fetchById(1)
        verify(mockRemoteDataSource, never()).getUserById(any())
    }

    @Test
    fun `fetch fetches from remote and syncs locally when local user is not found`() = runTest {
        val dto = dummyUserDTO(1, "johndoe")
        `when`(mockLocalDataSource.fetchById(1)).thenReturn(null)
        `when`(mockRemoteDataSource.getUserById(1)).thenReturn(dto)
        `when`(mockLocalDataSource.insert(any())).thenReturn(true)
        val results = mutableListOf<Result<UserModel>>()
        userRepository.fetch(1).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals("johndoe", results.first().getOrNull()?.username)
        verify(mockRemoteDataSource).getUserById(1)
        verify(mockLocalDataSource).insert(any())
    }

    @Test
    fun `fetch emits failure when user not found in local nor remote`() = runTest {
        `when`(mockLocalDataSource.fetchById(999)).thenReturn(null)
        `when`(mockRemoteDataSource.getUserById(999)).thenReturn(null)
        val results = mutableListOf<Result<UserModel>>()
        userRepository.fetch(999).toList(results)
        assertTrue(results.first().isFailure)
        assertTrue(results.first().exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun `fetch emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockLocalDataSource.fetchById(1)).thenThrow(exception)
        val results = mutableListOf<Result<UserModel>>()
        userRepository.fetch(1).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }

    @Test
    fun `update emits success when local update is successful`() = runTest {
        val model = UserModel(id = 1, username = "johndoe")
        `when`(mockLocalDataSource.update(any())).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.update(model).toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == true)
        verify(mockLocalDataSource).update(any())
    }

    @Test
    fun `update emits success false when local update fails`() = runTest {
        val model = UserModel(id = 1, username = "johndoe")
        `when`(mockLocalDataSource.update(any())).thenReturn(false)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.update(model).toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == false)
    }

    @Test
    fun `update emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("Source error")
        val model = UserModel(id = 1, username = "johndoe")
        `when`(mockLocalDataSource.update(any())).thenThrow(exception)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.update(model).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }

    @Test
    fun `location emits success with full address from first local user`() = runTest {
        val entity = dummyUserEntity(
            id = 1,
            username = "johndoe",
            street = "Main St",
            number = 42,
            city = "Springfield"
        )
        `when`(mockLocalDataSource.fetch()).thenReturn(listOf(entity))
        val results = mutableListOf<Result<String>>()
        userRepository.location().toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(entity.toModel().fullAddress, results.first().getOrNull())
        verify(mockLocalDataSource).fetch()
    }

    @Test
    fun `location emits failure when local list is empty`() = runTest {
        `when`(mockLocalDataSource.fetch()).thenReturn(emptyList())
        val results = mutableListOf<Result<String>>()
        userRepository.location().toList(results)
        assertTrue(results.first().isFailure)
        assertTrue(results.first().exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun `location emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("Source error")
        `when`(mockLocalDataSource.fetch()).thenThrow(exception)
        val results = mutableListOf<Result<String>>()
        userRepository.location().toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }
}