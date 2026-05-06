package com.deymervilla.testfakestore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.DATABASE_VERSION
import com.deymervilla.testfakestore.data.database.dao.ProductDao
import com.deymervilla.testfakestore.data.database.dao.UserDao
import com.deymervilla.testfakestore.data.database.entities.UserEntity
import com.deymervilla.testfakestore.data.database.entities.ProductEntity

@Database(
    entities = [UserEntity::class, ProductEntity::class],
    version = DATABASE_VERSION
)
abstract class RoomDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getProductDao(): ProductDao
}