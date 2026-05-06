package com.deymervilla.testfakestore.domain.repositories.user

import com.deymervilla.testfakestore.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun fetch(userId: Int): Flow<Result<UserModel>>

    fun update(user: UserModel): Flow<Result<Boolean>>

    fun location(): Flow<Result<String>>
}