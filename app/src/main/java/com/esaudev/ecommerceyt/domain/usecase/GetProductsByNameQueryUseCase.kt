package com.esaudev.ecommerceyt.domain.usecase

import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.repository.FavoritesRepository
import com.esaudev.ecommerceyt.domain.repository.ProductRepository
import com.esaudev.ecommerceyt.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class GetProductsByNameQueryUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(query: String): Flow<Resource<List<Product>>> = channelFlow {

        val networkResult = productRepository.fetchAllProducts()

        if (networkResult is Resource.Error) {
            send(Resource.Error(networkResult.error))
        }

        networkResult as Resource.Success
        favoritesRepository.getFavoriteIds()
            .flowOn(Dispatchers.IO)
            .collect() { favorites ->
                productRepository.updateProductsCache(products = networkResult.data, favorites)
                val productList = productRepository.getAllProductsCache()
                send(Resource.Success(productList.filter { it.name.contains(query) }))
            }
    }
}