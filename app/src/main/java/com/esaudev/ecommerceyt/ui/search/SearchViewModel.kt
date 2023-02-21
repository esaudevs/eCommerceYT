package com.esaudev.ecommerceyt.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.ecommerceyt.domain.model.Product
import com.esaudev.ecommerceyt.domain.model.RecentSearch
import com.esaudev.ecommerceyt.domain.usecase.GetAllProductsUseCase
import com.esaudev.ecommerceyt.domain.usecase.GetAllRecentSearchsUseCase
import com.esaudev.ecommerceyt.domain.usecase.SaveRecentSearchUseCase
import com.esaudev.ecommerceyt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val saveRecentSearchUseCase: SaveRecentSearchUseCase,
    private val getAllRecentSearchsUseCase: GetAllRecentSearchsUseCase
): ViewModel() {

    private val _recentSearch: MutableLiveData<List<RecentSearch>> = MutableLiveData()
    val recentSearch: LiveData<List<RecentSearch>>
        get() = _recentSearch

    init {
        getAllRecentSearch()
    }

    private fun getAllRecentSearch() {
        viewModelScope.launch {
            getAllRecentSearchsUseCase().onEach {
                _recentSearch.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun saveRecentSearch(recentSearch: RecentSearch) {
        viewModelScope.launch {
            saveRecentSearchUseCase(recentSearch)
        }
    }

}