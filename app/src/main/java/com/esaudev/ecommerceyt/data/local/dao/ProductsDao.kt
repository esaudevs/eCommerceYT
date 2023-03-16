package com.esaudev.ecommerceyt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.esaudev.ecommerceyt.data.local.entity.ProductEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM productentity")
    fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = REPLACE)
    fun insertAllProducts(list: List<ProductEntity>)

    @Query("DELETE FROM productentity")
    fun nukeTable()

    @Transaction
    fun updateAllProducts(list: List<ProductEntity>) {
        nukeTable()
        insertAllProducts(list)
    }

}