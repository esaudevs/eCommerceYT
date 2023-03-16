package com.esaudev.ecommerceyt.domain.usecase

import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.repository.FavoritesRepository
import com.esaudev.ecommerceyt.domain.repository.ProductRepository
import com.esaudev.ecommerceyt.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class GetAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Product>>> = channelFlow {

        val networkCall = productRepository.fetchAllProducts()

        if (networkCall is Resource.Error) {
            send(Resource.Error(networkCall.error))
        }

        networkCall as Resource.Success
        val favorites = favoritesRepository.getFavoriteIds()

        favorites
            .flowOn(Dispatchers.IO)
            .collect {
            productRepository.updateProductsCache(products = networkCall.data, it)
            val productList = productRepository.getAllProductsCache()
            send(Resource.Success(productList))
        }

    }
}