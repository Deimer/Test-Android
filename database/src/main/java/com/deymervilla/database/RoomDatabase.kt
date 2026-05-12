package com.deymervilla.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deymervilla.database.constants.DatabaseConstants.DATABASE_VERSION
import com.deymervilla.database.dao.ProductDao
import com.deymervilla.database.dao.UserDao
import com.deymervilla.database.entities.ProductEntity
import com.deymervilla.database.entities.UserEntity

@Database(
    entities = [UserEntity::class, ProductEntity::class],
    version = DATABASE_VERSION
)
abstract class RoomDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getProductDao(): ProductDao
}