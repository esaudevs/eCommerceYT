package com.esaudev.ecommerceyt.ui.result

import androidx.lifecycle.*
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.repository.FavoriteRepository
import com.esaudev.ecommerceyt.domain.usecase.GetProductsByNameQueryUseCase
import com.esaudev.ecommerceyt.utils.UiState
import com.esaudev.ecommerceyt.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getProductsByNameQueryUseCase: GetProductsByNameQueryUseCase,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _products: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val products: LiveData<UiState<List<Product>>>
        get() = _products

    private val searchQuery: String

    init {
        searchQuery = savedStateHandle.get<String>("nameQuery").orEmpty()
        getProductByNameQuery(searchQuery)
    }

    private fun getProductByNameQuery(queryName: String) {
        viewModelScope.launch {
            _products.postValue(UiState.Loading)

            val productsList = getProductsByNameQueryUseCase(query = queryName)
            _products.postValue(productsList.mapToUiState())
        }
    }

    fun saveFavorite(id: String) {
        viewModelScope.launch {
            favoriteRepository.saveFavorite(id)

            val productsList = getProductsByNameQueryUseCase(query = searchQuery)
            _products.postValue(productsList.mapToUiState())
        }
    }

    fun deleteFavorite(id: String) {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(id)

            val productsList = getProductsByNameQueryUseCase(query = searchQuery)
            _products.postValue(productsList.mapToUiState())
        }
    }

}