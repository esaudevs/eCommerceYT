package com.esaudev.ecommerceyt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.esaudev.ecommerceyt.data.local.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recentsearchentity")
    fun getAllRecentSearch(): Flow<List<RecentSearchEntity>>

    @Insert(onConflict = REPLACE)
    fun insertRecentSearch(recentSearchEntity: RecentSearchEntity)
}