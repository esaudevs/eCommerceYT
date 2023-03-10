package com.esaudev.ecommerceyt.data.repository

import com.esaudev.ecommerceyt.data.local.dao.FavoritesDao
import com.esaudev.ecommerceyt.data.local.entity.FavoriteEntity
import com.esaudev.ecommerceyt.data.local.entity.toId
import com.esaudev.ecommerceyt.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
): FavoriteRepository {

    override suspend fun saveFavorite(id: String) {
        withContext(Dispatchers.IO) {
            favoritesDao.insertFavoriteId(favoriteEntity = FavoriteEntity(id))
        }
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoritesDao.getFavorites().map { it.toId() }
    }

    override suspend fun deleteFavorite(id: String) {
        withContext(Dispatchers.IO) {
            favoritesDao.deleteFavoriteId(id)
        }
    }


}