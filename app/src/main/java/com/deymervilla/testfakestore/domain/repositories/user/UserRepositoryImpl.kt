package com.deymervilla.testfakestore.domain.repositories.user

import com.deymervilla.testfakestore.data.datasource.local.user.UserLocalDataSource
import com.deymervilla.testfakestore.data.datasource.remote.user.UserRemoteDataSource
import com.deymervilla.testfakestore.domain.mappers.toEntity
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

    override fun fetch(userId: Int) = flow {
        val result: Result<UserModel> = try {
            userLocalDataSource.fetchById(userId)?.toModel()?.let { user ->
                Result.success(user)
            } ?: run {
                userRemoteDataSource.getUserById(userId)?.let { remoteUser ->
                    userLocalDataSource.insert(listOf(remoteUser.toEntity()))
                    Result.success(remoteUser.toModel())
                } ?: Result.failure(NoSuchElementException(""))
            }
        } catch (ioException: IOException) {
            Result.failure(ioException)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
        emit(result)
    }

    override fun update(user: UserModel) = flow {
        try {
            emit(value = Result.success(userLocalDataSource.update(user.toEntity())))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun location() = flow {
        try {
            emit(value = Result.success(userLocalDataSource.fetch().first().toModel().fullAddress))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }
}