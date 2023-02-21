package com.esaudev.ecommerceyt.domain.ui

import com.esaudev.ecommerceyt.domain.model.INVALID_PRICE

data class ProductUi(
    val id: String? = null,
    val name: String = "",
    val brand: String = "",
    val price: Int = INVALID_PRICE,
    val image: String = "",
    val isFavorite: Boolean = false
)
