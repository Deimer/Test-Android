package com.deymervilla.testfakestore.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Columns.ID
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Columns.IS_FAVORITE
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Columns.TITLE
import com.deymervilla.testfakestore.data.database.constants.DatabaseConstants.Tables.PRODUCT_TABLE
import com.deymervilla.testfakestore.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>): List<Long>

    @Update
    suspend fun update(product: ProductEntity): Int

    @Delete
    suspend fun delete(product: ProductEntity): Int

    @Query("DELETE FROM $PRODUCT_TABLE")
    suspend fun delete(): Int

    @Query("SELECT * FROM $PRODUCT_TABLE")
    suspend fun fetchAll(): List<ProductEntity>

    @Query("SELECT * FROM $PRODUCT_TABLE WHERE $ID = :productId")
    suspend fun fetchById(productId: Int): ProductEntity?

    @Query("SELECT * FROM $PRODUCT_TABLE WHERE $TITLE LIKE :title")
    suspend fun fetchByTitle(title: String): List<ProductEntity>

    @Query("SELECT * FROM $PRODUCT_TABLE WHERE $IS_FAVORITE = 1")
    suspend fun fetchFavorites(): List<ProductEntity>
}