package com.deymervilla.testfakestore.domain.di

import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import com.deymervilla.testfakestore.domain.repositories.product.ProductRepositoryImpl
import com.deymervilla.testfakestore.domain.repositories.user.UserRepository
import com.deymervilla.testfakestore.domain.repositories.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindProductRepository(
        userRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}