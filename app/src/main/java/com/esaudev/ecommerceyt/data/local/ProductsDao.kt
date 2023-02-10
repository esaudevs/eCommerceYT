package com.esaudev.ecommerceyt.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ProductsDao {

    @Query("SELECT * FROM productentity")
    fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = REPLACE)
    fun insertAllProducts(list: List<ProductEntity>)

}