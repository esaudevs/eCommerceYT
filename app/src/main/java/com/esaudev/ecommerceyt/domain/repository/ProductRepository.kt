package com.esaudev.ecommerceyt.domain.repository

import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.utils.Resource

interface ProductRepository {

    suspend fun fetchAllProducts(): Resource<List<Product>>

    suspend fun getAllProductsCache(): List<Product>

    suspend fun insertAllProductsCache(products: List<Product>, favorites: List<String>)
}