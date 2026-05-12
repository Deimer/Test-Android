package com.deymervilla.database.constants

import com.deymervilla.database.BuildConfig

object DatabaseConstants {

    const val DATABASE_VERSION = 1
    const val KEY_NAME_DATABASE = BuildConfig.DATABASE_NAME

    object Tables {
        const val USER_TABLE = "user_table"
        const val PRODUCT_TABLE = "character_table"
    }

    object Columns {
        //Commons
        const val ID = "id"
        const val IMAGE = "image"
        const val IS_FAVORITE = "is_favorite"
        //Product
        const val TITLE = "title"
        const val PRICE = "price"
        const val DESCRIPTION = "description"
        const val CATEGORY = "category"
        const val RATING_RATE = "rating_rate"
        const val RATING_COUNT = "rating_count"
        //User
        const val EMAIL = "email"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val PHONE = "phone"
        const val CITY = "city"
        const val STREET = "street"
        const val NUMBER = "number"
        const val ZIPCODE = "zipcode"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}