package com.deymervilla.testfakestore.usecase.user

import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.domain.repositories.user.UserRepository
import com.deymervilla.testfakestore.domain.usecases.user.UpdateUserUseCase
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
class UpdateUserUseCaseTest {

    @Mock
    private lateinit var mockRepository: UserRepository

    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        updateUserUseCase = UpdateUserUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success true when repository updates user successfully`() = runTest {
        val user = UserModel(id = 1, username = "johndoe")
        `when`(mockRepository.update(user)).thenReturn(flowOf(Result.success(true)))
        val results = updateUserUseCase(user).toList()
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == true)
        verify(mockRepository).update(user)
    }

    @Test
    fun `invoke should emit success false when repository fails to update user`() = runTest {
        val user = UserModel(id = 1, username = "johndoe")
        `when`(mockRepository.update(user)).thenReturn(flowOf(Result.success(false)))
        val results = updateUserUseCase(user).toList()
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == false)
        verify(mockRepository).update(user)
    }

    @Test
    fun `invoke should emit failure when repository throws exception on update`() = runTest {
        val user = UserModel(id = 1, username = "johndoe")
        val exception = RuntimeException("Source error")
        `when`(mockRepository.update(user)).thenReturn(flowOf(Result.failure(exception)))
        val results = updateUserUseCase(user).toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).update(user)
    }
}