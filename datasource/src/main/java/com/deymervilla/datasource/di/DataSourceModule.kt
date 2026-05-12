package com.deymervilla.datasource.di

import com.deymervilla.datasource.local.product.ProductLocalDataSource
import com.deymervilla.datasource.local.product.ProductLocalDataSourceImpl
import com.deymervilla.datasource.local.user.UserLocalDataSource
import com.deymervilla.datasource.local.user.UserLocalDataSourceImpl
import com.deymervilla.datasource.remote.product.ProductRemoteDataSource
import com.deymervilla.datasource.remote.product.ProductRemoteDataSourceImpl
import com.deymervilla.datasource.remote.user.UserRemoteDataSource
import com.deymervilla.datasource.remote.user.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindUserRemoteDataSource(
        remoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindUserLocalDataSource(
        localDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindProductRemoteDataSource(
        remoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

    @Binds
    abstract fun bindProductLocalDataSource(
        localDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource
}