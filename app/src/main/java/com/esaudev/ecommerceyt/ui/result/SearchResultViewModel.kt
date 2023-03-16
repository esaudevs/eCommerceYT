package com.esaudev.ecommerceyt.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.repository.FavoritesRepository
import com.esaudev.ecommerceyt.domain.usecase.GetProductsByNameQueryUseCase
import com.esaudev.ecommerceyt.utils.UiState
import com.esaudev.ecommerceyt.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getProductsByNameQueryUseCase: GetProductsByNameQueryUseCase,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _productsFlow = MutableStateFlow<UiState<List<Product>>?>(null)
    val productsFlow = _productsFlow.asStateFlow()

    private val query: String

    init {
        query = savedStateHandle.get<String>("nameQuery").orEmpty()
        getProductByNameQuery(query)
    }

    private fun getProductByNameQuery(queryName: String) {
        viewModelScope.launch {
            _productsFlow.value = UiState.Loading

            getProductsByNameQueryUseCase(query = queryName).onEach {
                _productsFlow.value = it.mapToUiState()
            }.launchIn(viewModelScope)
        }
    }

    fun saveFavorite(id: String) {
        viewModelScope.launch {
            favoritesRepository.saveFavorite(id)
        }
    }

    fun deleteFavorite(id: String) {
        viewModelScope.launch {
            favoritesRepository.deleteFavorite(id)
        }
    }

}