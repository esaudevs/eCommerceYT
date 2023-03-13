package com.esaudev.ecommerceyt.domain.model

import com.esaudev.ecommerceyt.data.local.entity.ProductEntity

const val INVALID_PRICE = -1

data class Product(
    val id: String = "",
    val name: String = "",
    val brand: String = "",
    val price: Int = INVALID_PRICE,
    val image: String = "",
    val isFavorite: Boolean = false
)

fun Product.mapToProductEntity(favorites: List<String>): ProductEntity {
    return ProductEntity(
        id = id.orEmpty(),
        name = name,
        brand = brand,
        price = price,
        image = image,
        isFavorite = favorites.contains(id)
    )
}

fun List<Product>.mapToProductEntityList(favorites: List<String>): List<ProductEntity> {
    return this.map { it.mapToProductEntity(favorites) }
}
