package com.deymervilla.datasource.remote.user

import com.deymervilla.network.api.ApiService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): UserRemoteDataSource {

    override suspend fun getUserById(userId: Int) =
        apiService.getUserById(userId)
}