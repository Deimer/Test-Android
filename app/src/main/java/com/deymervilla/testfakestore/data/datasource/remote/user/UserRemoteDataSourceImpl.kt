package com.deymervilla.testfakestore.data.datasource.remote.user

import com.deymervilla.testfakestore.data.network.api.ApiService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): UserRemoteDataSource {

    override suspend fun getUserById(userId: Int) =
        apiService.getUserById(userId)
}