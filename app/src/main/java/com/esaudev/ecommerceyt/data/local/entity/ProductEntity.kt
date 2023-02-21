package com.esaudev.ecommerceyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esaudev.ecommerceyt.domain.model.INVALID_PRICE
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.ui.ProductUi

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String = "",
    val brand: String = "",
    val price: Int = INVALID_PRICE,
    val image: String = "",
    val isFavorite: Boolean = false
)

fun ProductEntity.mapToProductUi(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        brand = brand,
        price = price,
        image = image,
        isFavorite = isFavorite
    )
}

fun List<ProductEntity>.mapToProductUiList(): List<ProductUi> {
    return this.map { it.mapToProductUi() }
}

fun ProductEntity.mapToProduct(): Product {
    return Product(
        id = id,
        name = name,
        brand = brand,
        price = price,
        image = image
    )
}

fun List<ProductEntity>.mapToProductList(): List<Product> {
    return this.map { it.mapToProduct() }
}
