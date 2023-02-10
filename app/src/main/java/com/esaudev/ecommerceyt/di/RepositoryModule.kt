package com.esaudev.ecommerceyt.di

import com.esaudev.ecommerceyt.data.repository.ProductRepositoryImpl
import com.esaudev.ecommerceyt.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductsRepository(productsRepository: ProductRepositoryImpl): ProductRepository
}