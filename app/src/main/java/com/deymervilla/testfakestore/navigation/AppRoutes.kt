package com.deymervilla.testfakestore.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoutes: NavKey {

    @Serializable
    data object SplashScreen: AppRoutes

    @Serializable
    data object HomeScreen: AppRoutes

    @Serializable
    data object ProfileScreen: AppRoutes

    @Serializable
    data class ProductScreen(
        val productId: Int
    ): AppRoutes
}