package com.deymervilla.testfakestore.data.datasource.local.user

import com.deymervilla.testfakestore.data.database.entities.UserEntity

interface UserLocalDataSource {

    suspend fun fetchById(
        userId: Int
    ): UserEntity?

    suspend fun fetchByUsername(
        username: String
    ): UserEntity?

    suspend fun fetchByEmail(
        email: String
    ): UserEntity?

    suspend fun fetch(): List<UserEntity>

    suspend fun insert(users: List<UserEntity>): Boolean

    suspend fun delete(user: UserEntity): Boolean

    suspend fun update(user: UserEntity): Boolean
}