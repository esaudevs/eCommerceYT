package com.esaudev.ecommerceyt.data.repository

import com.esaudev.ecommerceyt.data.local.dao.RecentSearchDao
import com.esaudev.ecommerceyt.data.local.entity.mapToRecentSearchList
import com.esaudev.ecommerceyt.domain.model.RecentSearch
import com.esaudev.ecommerceyt.domain.model.mapToRecentSearchEntity
import com.esaudev.ecommerceyt.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
): RecentSearchRepository {

    override suspend fun getAllRecentSearch(): Flow<List<RecentSearch>> {
        return recentSearchDao.getAllRecentSearch().map { it.mapToRecentSearchList() }
    }

    override suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insertRecentSearch(recentSearch.mapToRecentSearchEntity())
    }

}