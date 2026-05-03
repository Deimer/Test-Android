package com.deymervilla.testfakestore.domain.repositories.user

import com.deymervilla.testfakestore.data.datasource.local.user.UserLocalDataSource
import com.deymervilla.testfakestore.data.datasource.remote.user.UserRemoteDataSource
import com.deymervilla.testfakestore.domain.mappers.toModel
import com.deymervilla.testfakestore.domain.models.UserModel
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun fetchUser(userId: Int) = flow {
        val result: Result<UserModel> = try {
            userLocalDataSource.fetchById(userId)?.toModel()?.let { character ->
                Result.success(character)
            } ?: run {
                userRemoteDataSource.getUserById(userId)?.toModel()?.let { character ->
                    Result.success(character)
                } ?: Result.failure(NoSuchElementException(""))
            }
        } catch (ioException: IOException) {
            Result.failure(ioException)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
        emit(result)
    }
}