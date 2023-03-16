package com.esaudev.ecommerceyt.data.repository

import com.esaudev.ecommerceyt.data.local.dao.ProductsDao
import com.esaudev.ecommerceyt.data.local.entity.mapToProductList
import com.esaudev.ecommerceyt.data.remote.FirestoreConstants.PRODUCTS_COLLECTION
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.model.mapToProductEntityList
import com.esaudev.ecommerceyt.domain.repository.ProductRepository
import com.esaudev.ecommerceyt.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val firestoreInstance: FirebaseFirestore
): ProductRepository {

    override suspend fun fetchAllProducts(): Resource<List<Product>> {
        return try {
            val productList = firestoreInstance.collection(PRODUCTS_COLLECTION)
                .get()
                .await()
                .toObjects(Product::class.java)

            Resource.Success(productList)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getAllProductsCache(): List<Product> {
        return withContext(Dispatchers.IO) {
            productsDao.getAllProducts().mapToProductList()
        }
    }

    override suspend fun insertAllProductsCache(products: List<Product>, favorites: List<String>) {
        productsDao.insertAllProducts(products.mapToProductEntityList(favorites))
    }

    override suspend fun updateAllProductsCache(products: List<Product>, favorites: List<String>) {
        withContext(Dispatchers.IO) {
            productsDao.updateAllProducts(products.mapToProductEntityList(favorites))
        }
    }

}