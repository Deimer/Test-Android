package com.deymervilla.testfakestore.data.datasource.remote.user

import com.deymervilla.testfakestore.data.network.dto.UserDTO

interface UserRemoteDataSource {

    suspend fun getUserById(
        userId: Int
    ): UserDTO?
}