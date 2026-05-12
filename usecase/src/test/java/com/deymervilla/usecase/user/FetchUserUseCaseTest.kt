package com.deymervilla.usecase.user

import com.deymervilla.repository.models.UserModel
import com.deymervilla.repository.repositories.user.UserRepository
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
class FetchUserUseCaseTest {

    @Mock
    private lateinit var mockRepository: UserRepository

    private lateinit var fetchUserUseCase: FetchUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchUserUseCase = FetchUserUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success when repository returns user by id`() = runTest {
        val expectedUser = UserModel(id = 1, username = "johndoe")
        `when`(mockRepository.fetch(1)).thenReturn(flowOf(Result.success(expectedUser)))
        val results = fetchUserUseCase(1).toList()
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedUser, results.first().getOrNull())
        verify(mockRepository).fetch(1)
    }

    @Test
    fun `invoke should emit failure when repository fails to fetch user`() = runTest {
        val exception = RuntimeException("User not found")
        `when`(mockRepository.fetch(999)).thenReturn(flowOf(Result.failure(exception)))
        val results = fetchUserUseCase(999).toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).fetch(999)
    }
}