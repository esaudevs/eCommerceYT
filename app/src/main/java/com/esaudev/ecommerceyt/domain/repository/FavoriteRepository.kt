package com.esaudev.ecommerceyt.domain.repository

interface FavoriteRepository {

    suspend fun saveFavorite(id: String)

    suspend fun getFavoriteIds(): List<String>

    suspend fun deleteFavorite(id: String)
}