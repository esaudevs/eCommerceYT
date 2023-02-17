package com.esaudev.ecommerceyt.domain.repository

import com.esaudev.ecommerceyt.domain.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {

    suspend fun getAllRecentSearch(): Flow<List<RecentSearch>>

    suspend fun insertRecentSearch(recentSearch: RecentSearch)
}