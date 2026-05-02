package com.deymervilla.testfakestore.data.database.di

import android.content.Context
import androidx.room.Room
import com.deymervilla.testfakestore.data.database.RoomDatabase
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.KEY_NAME_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        klass = RoomDatabase::class.java,
        name = KEY_NAME_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(
        database: RoomDatabase
    ) = database.getUserDao()

    @Singleton
    @Provides
    fun provideProductDao(
        database: RoomDatabase
    ) = database.getProductDao()
}