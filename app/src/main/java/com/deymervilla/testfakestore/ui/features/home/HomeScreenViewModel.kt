package com.deymervilla.testfakestore.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.domain.usecases.product.FetchFavoriteProductsUseCase
import com.deymervilla.testfakestore.domain.usecases.product.FetchProductsUseCase
import com.deymervilla.testfakestore.domain.usecases.user.FetchUserLocationUseCase
import com.deymervilla.testfakestore.ui.di.IoDispatcher
import com.deymervilla.testfakestore.ui.utils.default
import com.deymervilla.testfakestore.ui.utils.failure
import com.deymervilla.testfakestore.ui.utils.isIOEx
import com.deymervilla.testfakestore.ui.utils.launchIn
import com.deymervilla.testfakestore.ui.utils.map
import com.deymervilla.testfakestore.ui.utils.start
import com.deymervilla.testfakestore.ui.utils.success
import com.deymervilla.testfakestore.ui.utils.withMinDelay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data object Success: HomeUiState()
    data object ConnectionError: HomeUiState()
    data class Error(val message: String? = null): HomeUiState()
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchLocationUserCase: FetchUserLocationUseCase,
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val fetchFavoritesUseCase: FetchFavoriteProductsUseCase
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState = _homeUiState.asStateFlow()

    private val _isLocationLoading = MutableStateFlow(false)
    val isLocationLoading: StateFlow<Boolean> = _isLocationLoading.asStateFlow()

    private val _userLocation = MutableStateFlow("")
    val userLocation: StateFlow<String> = _userLocation.asStateFlow()

    private val _productList = MutableStateFlow<List<ProductModel>>(emptyList())
    val productList: StateFlow<List<ProductModel>> = _productList.asStateFlow()

    private val _favoriteList = MutableStateFlow<List<ProductModel>>(emptyList())
    val favoriteList: StateFlow<List<ProductModel>> = _favoriteList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchSuggestions: StateFlow<List<ProductModel>> = combine(
        _productList,
        _searchQuery
    ) { products, query ->
        if (query.isEmpty()) {
            emptyList()
        } else {
            products.filter { it.title.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init { getProducts() }

    private fun getProducts() {
        fetchProductsUseCase().start {
            _homeUiState.emit(HomeUiState.Loading)
        }.map { products ->
            _productList.value = products
        }.success {
            _homeUiState.emit(HomeUiState.Success)
        }.failure { exception ->
            exception.isIOEx {
                _homeUiState.emit(HomeUiState.ConnectionError)
            }
            exception.default {
                _homeUiState.emit(HomeUiState.Error(exception.message.orEmpty()))
            }
        }.onCompletion {
            getFavorites()
            getLocation()
        }.launchIn(viewModelScope, ioDispatcher)
    }

    private fun getFavorites() {
        fetchFavoritesUseCase().onEach { favorites ->
            _favoriteList.value = favorites
            _homeUiState.emit(HomeUiState.Success)
        }.catch { exception ->
            _homeUiState.emit(HomeUiState.Error(exception.message))
        }.launchIn(viewModelScope, ioDispatcher)
    }

    fun getLocation() {
        fetchLocationUserCase().withMinDelay(700).start {
            _isLocationLoading.value = true
        }.map { fullAddress ->
            _userLocation.value = fullAddress
        }.success {
            _isLocationLoading.value = false
        }.failure { exception ->
            _isLocationLoading.value = false
        }.launchIn(viewModelScope, ioDispatcher)
    }

    fun onSearchChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}