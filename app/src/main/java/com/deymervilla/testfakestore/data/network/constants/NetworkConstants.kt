package com.deymervilla.testfakestore.data.network.constants

object NetworkConstants {

    object DEFAULTS {
        const val DEFAULT_TIMEOUT = 10L
        const val DEFAULT_LIMIT = 20
    }

    object PARAMETERS {
        const val PARAMETER_CHARACTER = "productId"
    }

    object URLs {
        const val PRODUCTS_PATH = "products"
        const val PRODUCT_PATH = "products/{productId}"
        const val USERS_PATH = "users"
        const val USER_PATH = "users/{userId}"
    }
}