package com.esaudev.ecommerceyt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.esaudev.ecommerceyt.data.local.entity.FavoriteEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favoriteentity")
    suspend fun getFavorites(): List<FavoriteEntity>

    @Query("DELETE FROM favoriteentity WHERE id=:id")
    suspend fun deleteFavorite(id: String)
}