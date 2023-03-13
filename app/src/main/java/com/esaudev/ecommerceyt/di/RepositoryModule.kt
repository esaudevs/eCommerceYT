package com.esaudev.ecommerceyt.di

import com.esaudev.ecommerceyt.data.repository.FavoritesRepositoryImpl
import com.esaudev.ecommerceyt.data.repository.ProductRepositoryImpl
import com.esaudev.ecommerceyt.data.repository.RecentSearchRepositoryImpl
import com.esaudev.ecommerceyt.domain.repository.FavoritesRepository
import com.esaudev.ecommerceyt.domain.repository.ProductRepository
import com.esaudev.ecommerceyt.domain.repository.RecentSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductsRepository(productsRepository: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindRecentSearchRepository(recentSearchRepository: RecentSearchRepositoryImpl): RecentSearchRepository

    @Binds
    abstract fun bindFavoritesRepository(favoritesRepository: FavoritesRepositoryImpl): FavoritesRepository
}