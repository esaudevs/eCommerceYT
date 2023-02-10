package com.esaudev.ecommerceyt.utils

sealed class Resource <out R> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val error: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}
