package com.deymervilla.testfakestore.domain.repositories.user

import com.deymervilla.testfakestore.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun fetchUser(userId: Int): Flow<Result<UserModel>>
}