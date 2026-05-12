package com.deymervilla.datasource.local.user

import com.deymervilla.database.dao.UserDao
import com.deymervilla.database.entities.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
): UserLocalDataSource {

    override suspend fun fetchById(userId: Int) =
        userDao.fetchById(userId)

    override suspend fun fetchByUsername(username: String) =
        userDao.fetchByUsername(username)

    override suspend fun fetchByEmail(email: String) =
        userDao.fetchByEmail(email)

    override suspend fun fetch() =
        userDao.fetchAll()

    override suspend fun insert(users: List<UserEntity>): Boolean {
        val result = userDao.insert(users)
        return result.isNotEmpty() && result.all { it > 0 }
    }

    override suspend fun delete(user: UserEntity) =
        userDao.delete(user) == 1

    override suspend fun update(user: UserEntity) =
        userDao.update(user) == 1
}