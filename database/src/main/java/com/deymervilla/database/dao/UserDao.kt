package com.deymervilla.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.deymervilla.database.constants.DatabaseConstants.Columns.EMAIL
import com.deymervilla.database.constants.DatabaseConstants.Columns.ID
import com.deymervilla.database.constants.DatabaseConstants.Columns.USERNAME
import com.deymervilla.database.constants.DatabaseConstants.Tables.USER_TABLE
import com.deymervilla.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserEntity>): List<Long>

    @Update
    suspend fun update(user: UserEntity): Int

    @Delete
    suspend fun delete(user: UserEntity): Int

    @Query("DELETE FROM $USER_TABLE")
    suspend fun delete(): Int

    @Query("SELECT * FROM $USER_TABLE")
    suspend fun fetchAll(): List<UserEntity>

    @Query("SELECT * FROM $USER_TABLE WHERE $ID = :userId")
    suspend fun fetchById(userId: Int): UserEntity?

    @Query("SELECT * FROM $USER_TABLE WHERE $USERNAME = :username")
    suspend fun fetchByUsername(username: String): UserEntity?

    @Query("SELECT * FROM $USER_TABLE WHERE $EMAIL = :email")
    suspend fun fetchByEmail(email: String): UserEntity?
}