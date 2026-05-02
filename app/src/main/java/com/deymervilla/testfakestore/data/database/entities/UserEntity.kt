package com.deymervilla.testfakestore.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Tables
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Columns

@Entity(tableName = Tables.USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Columns.ID)
    val id: Int,
    @ColumnInfo(name = Columns.EMAIL)
    val email: String,
    @ColumnInfo(name = Columns.USERNAME)
    val username: String,
    @ColumnInfo(name = Columns.PHONE)
    val phone: String,
    @ColumnInfo(name = Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = Columns.CITY)
    val city: String,
    @ColumnInfo(name = Columns.STREET)
    val street: String,
    @ColumnInfo(name = Columns.NUMBER)
    val number: Int,
    @ColumnInfo(name = Columns.ZIPCODE)
    val zipcode: String,
    @ColumnInfo(name = Columns.LATITUDE)
    val latitude: String,
    @ColumnInfo(name = Columns.LONGITUDE)
    val longitude: String
)