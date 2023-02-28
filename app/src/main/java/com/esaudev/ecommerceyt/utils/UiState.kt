package com.esaudev.ecommerceyt.utils

sealed class UiState <out R> {
    data class Success<out T>(val data: T): UiState<T>()
    data class Error(val error: Exception): UiState<Nothing>()
    object Loading: UiState<Nothing>()
}
