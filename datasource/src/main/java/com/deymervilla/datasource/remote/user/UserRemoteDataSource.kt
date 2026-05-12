package com.deymervilla.datasource.remote.user

import com.deymervilla.network.dto.UserDTO

interface UserRemoteDataSource {

    suspend fun getUserById(
        userId: Int
    ): UserDTO?
}