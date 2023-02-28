package com.esaudev.ecommerceyt.utils

sealed class Resource <out R> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val error: Exception): Resource<Nothing>()
}

fun<T> Resource<T>.mapToUiState(): UiState<T> {
    return when(this) {
        is Resource.Success -> UiState.Success(data = data)
        is Resource.Error -> UiState.Error(error = error)
        else -> throw IllegalStateException("Resource type not supported")
    }
}
