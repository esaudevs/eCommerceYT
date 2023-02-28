package com.esaudev.ecommerceyt.ui.result

import androidx.lifecycle.*
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.usecase.GetProductsByNameQueryUseCase
import com.esaudev.ecommerceyt.utils.Resource
import com.esaudev.ecommerceyt.utils.UiState
import com.esaudev.ecommerceyt.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getProductsByNameQueryUseCase: GetProductsByNameQueryUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _products: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val products: LiveData<UiState<List<Product>>>
        get() = _products

    init {
        val nameQuery = savedStateHandle.get<String>("nameQuery").orEmpty()
        getProductByNameQuery(nameQuery)
    }

    private fun getProductByNameQuery(queryName: String) {
        viewModelScope.launch {
            _products.postValue(UiState.Loading)

            val productsList = getProductsByNameQueryUseCase(query = queryName)
            _products.postValue(productsList.mapToUiState())
        }
    }

}