package com.deymervilla.repository.repositories.user

import com.deymervilla.repository.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun fetch(userId: Int): Flow<Result<UserModel>>

    fun update(user: UserModel): Flow<Result<Boolean>>

    fun location(): Flow<Result<String>>
}