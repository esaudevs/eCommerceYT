package com.esaudev.ecommerceyt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esaudev.ecommerceyt.data.local.dao.ProductsDao
import com.esaudev.ecommerceyt.data.local.dao.RecentSearchDao
import com.esaudev.ecommerceyt.data.local.entity.ProductEntity
import com.esaudev.ecommerceyt.data.local.entity.RecentSearchEntity

@Database(entities = [ProductEntity::class, RecentSearchEntity::class], version = 1)
abstract class EcommerceDatabase: RoomDatabase() {

    abstract fun productsDao(): ProductsDao
    abstract fun recentSearchDao(): RecentSearchDao
}