package com.esaudev.ecommerceyt.data.repository

import com.esaudev.ecommerceyt.data.local.dao.FavoritesDao
import com.esaudev.ecommerceyt.data.local.entity.FavoriteEntity
import com.esaudev.ecommerceyt.domain.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
): FavoritesRepository{

    override suspend fun saveFavorite(id: String) {
        withContext(Dispatchers.IO) {
            favoritesDao.insertFavorite(favoriteEntity = FavoriteEntity(id))
        }
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoritesDao.getFavorites().map { it.id }
    }

    override suspend fun deleteFavorite(id: String) {
        favoritesDao.deleteFavorite(id)
    }


}