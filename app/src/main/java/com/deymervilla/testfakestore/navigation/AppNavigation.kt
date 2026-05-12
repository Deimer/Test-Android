package com.deymervilla.testfakestore.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.deymervilla.testfakestore.features.home.HomeScreenActions
import com.deymervilla.testfakestore.features.home.HomeScreenAttributes
import com.deymervilla.testfakestore.features.home.HomeScreenCompose
import com.deymervilla.testfakestore.features.product.ProductScreenActions
import com.deymervilla.testfakestore.features.product.ProductScreenAttributes
import com.deymervilla.testfakestore.features.product.ProductScreenCompose
import com.deymervilla.testfakestore.features.profile.ProfileScreenActions
import com.deymervilla.testfakestore.features.profile.ProfileScreenAttributes
import com.deymervilla.testfakestore.features.profile.ProfileScreenCompose
import com.deymervilla.testfakestore.features.splash.SplashScreenActions
import com.deymervilla.testfakestore.features.splash.SplashScreenCompose
import com.deymervilla.testfakestore.presentation.components.SnackBarCompose

@Composable
fun AppNavigation(
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackBarCompose(snackbarHostState) },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                BodyCompose(
                    snackbarHostState = snackbarHostState,
                )
            }
        }
    )
}

@Composable
private fun BodyCompose(
    snackbarHostState: SnackbarHostState
) {
    val backStack = rememberNavBackStack(AppRoutes.SplashScreen)
    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<AppRoutes.SplashScreen> {
                SplashScreenCompose(actions = SplashScreenActions(
                    onPrimaryAction = {
                        backStack.removeLastOrNull()
                        backStack.add(AppRoutes.HomeScreen)
                    },
                ))
            }
            entry<AppRoutes.HomeScreen> {
                HomeScreenCompose(attributes = HomeScreenAttributes(
                    snackbarHostState = snackbarHostState,
                    actions = HomeScreenActions(
                        onPrimaryAction = {
                            backStack.removeLastOrNull()
                        },
                        onSecondaryAction = { productId ->
                            backStack.add(
                                AppRoutes.ProductScreen(
                                    productId = productId
                                )
                            )
                        },
                        onTertiaryAction = {
                            backStack.add(AppRoutes.ProfileScreen)
                        }
                    ),
                ))
            }
            entry<AppRoutes.ProductScreen> { key ->
                ProductScreenCompose(attributes = ProductScreenAttributes(
                    productId = key.productId,
                    snackbarHostState = snackbarHostState,
                    actions = ProductScreenActions(
                        onPrimaryAction = {
                            backStack.removeLastOrNull()
                        },
                    ),
                ))
            }
            entry<AppRoutes.ProfileScreen> { key ->
                ProfileScreenCompose(attributes = ProfileScreenAttributes(
                    snackbarHostState = snackbarHostState,
                    actions = ProfileScreenActions(
                        onPrimaryAction = {
                            backStack.removeLastOrNull()
                        },
                    ),
                ))
            }
        }
    )
}