package com.deymervilla.testfakestore.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Tables
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Columns

@Entity(tableName = Tables.PRODUCT_TABLE)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Columns.ID)
    val id: Int,
    @ColumnInfo(name = Columns.TITLE)
    val title: String,
    @ColumnInfo(name = Columns.PRICE)
    val price: Double,
    @ColumnInfo(name = Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = Columns.CATEGORY)
    val category: String,
    @ColumnInfo(name = Columns.IMAGE)
    val image: String,
    @ColumnInfo(name = Columns.RATING_RATE)
    val rate: Double,
    @ColumnInfo(name = Columns.RATING_COUNT)
    val count: Int,
    @ColumnInfo(name = Columns.IS_FAVORITE)
    val isFavorite: Boolean = false
)