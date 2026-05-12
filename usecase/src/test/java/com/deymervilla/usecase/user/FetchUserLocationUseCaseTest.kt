package com.deymervilla.usecase.user

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
class FetchUserLocationUseCaseTest {

    @Mock
    private lateinit var mockRepository: UserRepository

    private lateinit var fetchUserLocationUseCase: FetchUserLocationUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchUserLocationUseCase = FetchUserLocationUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit success with user location`() = runTest {
        val expectedLocation = "Main St 42, Springfield"
        `when`(mockRepository.location()).thenReturn(flowOf(Result.success(expectedLocation)))
        val results = fetchUserLocationUseCase().toList()
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedLocation, results.first().getOrNull())
        verify(mockRepository).location()
    }

    @Test
    fun `invoke should emit failure when repository fails to fetch location`() = runTest {
        val exception = RuntimeException("Location not found")
        `when`(mockRepository.location()).thenReturn(flowOf(Result.failure(exception)))
        val results = fetchUserLocationUseCase().toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockRepository).location()
    }
}