package com.deymervilla.testfakestore.data.datasource.di

import com.deymervilla.testfakestore.data.datasource.local.product.ProductLocalDataSource
import com.deymervilla.testfakestore.data.datasource.local.product.ProductLocalDataSourceImpl
import com.deymervilla.testfakestore.data.datasource.local.user.UserLocalDataSource
import com.deymervilla.testfakestore.data.datasource.local.user.UserLocalDataSourceImpl
import com.deymervilla.testfakestore.data.datasource.remote.product.ProductRemoteDataSource
import com.deymervilla.testfakestore.data.datasource.remote.product.ProductRemoteDataSourceImpl
import com.deymervilla.testfakestore.data.datasource.remote.user.UserRemoteDataSource
import com.deymervilla.testfakestore.data.datasource.remote.user.UserRemoteDataSourceImpl
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