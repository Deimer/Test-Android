package com.deymervilla.testfakestore.di

import com.deymervilla.repository.repositories.product.ProductRepository
import com.deymervilla.repository.repositories.user.UserRepository
import com.deymervilla.usecase.product.FetchFavoriteProductsUseCase
import com.deymervilla.usecase.product.FetchProductByIdUseCase
import com.deymervilla.usecase.product.FetchProductsByNameUseCase
import com.deymervilla.usecase.product.FetchProductsUseCase
import com.deymervilla.usecase.product.SetProductFavoriteUseCase
import com.deymervilla.usecase.user.FetchUserLocationUseCase
import com.deymervilla.usecase.user.FetchUserUseCase
import com.deymervilla.usecase.user.UpdateUserUseCase
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
    fun provideFetchUserLocationUseCase(
        userRepository: UserRepository
    ) = FetchUserLocationUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateUserUseCase(
        userRepository: UserRepository
    ) = UpdateUserUseCase(userRepository)

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