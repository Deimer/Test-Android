package com.deymervilla.testfakestore.domain.di

import com.deymervilla.testfakestore.domain.repositories.product.ProductRepository
import com.deymervilla.testfakestore.domain.repositories.user.UserRepository
import com.deymervilla.testfakestore.domain.usecases.product.FetchFavoriteProductsUseCase
import com.deymervilla.testfakestore.domain.usecases.product.FetchProductByIdUseCase
import com.deymervilla.testfakestore.domain.usecases.product.FetchProductsByNameUseCase
import com.deymervilla.testfakestore.domain.usecases.product.FetchProductsUseCase
import com.deymervilla.testfakestore.domain.usecases.product.SetProductFavoriteUseCase
import com.deymervilla.testfakestore.domain.usecases.user.FetchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFetchUserUseCase(
        userRepository: UserRepository
    ) = FetchUserUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchProductsUseCase(
        productRepository: ProductRepository
    ) = FetchProductsUseCase(productRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchProductByIdUseCase(
        productRepository: ProductRepository
    ) = FetchProductByIdUseCase(productRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchProductsByNameUseCase(
        productRepository: ProductRepository
    ) = FetchProductsByNameUseCase(productRepository)

    @Provides
    @ViewModelScoped
    fun provideSetProductFavoriteUseCase(
        productRepository: ProductRepository
    ) = SetProductFavoriteUseCase(productRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchFavoriteProductsUseCase(
        productRepository: ProductRepository
    ) = FetchFavoriteProductsUseCase(productRepository)
}