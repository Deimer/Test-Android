package com.deymervilla.testfakestore.domain.repositories.user

import com.deymervilla.testfakestore.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUser(userId: Int): Flow<Result<UserModel>>
}