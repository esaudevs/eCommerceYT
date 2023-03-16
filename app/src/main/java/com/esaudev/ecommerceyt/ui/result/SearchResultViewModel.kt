package com.esaudev.ecommerceyt.ui.result

import androidx.lifecycle.*
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.repository.FavoritesRepository
import com.esaudev.ecommerceyt.domain.usecase.GetProductsByNameQueryUseCase
import com.esaudev.ecommerceyt.utils.Resource
import com.esaudev.ecommerceyt.utils.UiState
import com.esaudev.ecommerceyt.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getProductsByNameQueryUseCase: GetProductsByNameQueryUseCase,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _products: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val products: LiveData<UiState<List<Product>>>
        get() = _products

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