package com.esaudev.ecommerceyt.ui.result

import androidx.lifecycle.*
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.usecase.GetProductsByNameQueryUseCase
import com.esaudev.ecommerceyt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getProductsByNameQueryUseCase: GetProductsByNameQueryUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val products: LiveData<Resource<List<Product>>>
        get() = _products

    init {
        val nameQuery = savedStateHandle.get<String>("nameQuery").orEmpty()
        getProductByNameQuery(nameQuery)
    }

    private fun getProductByNameQuery(queryName: String) {
        viewModelScope.launch {
            val productsList = getProductsByNameQueryUseCase(query = queryName)
            _products.postValue(productsList)
        }
    }

}