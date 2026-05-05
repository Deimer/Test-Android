package com.deymervilla.testfakestore.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deymervilla.testfakestore.domain.models.ProductModel
import com.deymervilla.testfakestore.domain.usecases.product.FetchFavoriteProductsUseCase
import com.deymervilla.testfakestore.domain.usecases.product.FetchProductsUseCase
import com.deymervilla.testfakestore.domain.usecases.user.FetchUserUseCase
import com.deymervilla.testfakestore.ui.di.IoDispatcher
import com.deymervilla.testfakestore.ui.utils.default
import com.deymervilla.testfakestore.ui.utils.failure
import com.deymervilla.testfakestore.ui.utils.isIOEx
import com.deymervilla.testfakestore.ui.utils.launchIn
import com.deymervilla.testfakestore.ui.utils.map
import com.deymervilla.testfakestore.ui.utils.start
import com.deymervilla.testfakestore.ui.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
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
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val fetchFavoritesUseCase: FetchFavoriteProductsUseCase
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState = _homeUiState.asStateFlow()

    private val _userLocation = MutableStateFlow("")
    val userLocation: StateFlow<String> = _userLocation.asStateFlow()

    private val _productList = MutableStateFlow<List<ProductModel>>(emptyList())
    val productList: StateFlow<List<ProductModel>> = _productList.asStateFlow()

    private val _favoriteList = MutableStateFlow<List<ProductModel>>(emptyList())
    val favoriteList: StateFlow<List<ProductModel>> = _favoriteList.asStateFlow()

    init { fetchUser() }

    fun fetchUser() {
        fetchUserUseCase(8).start {
            _homeUiState.emit(HomeUiState.Loading)
        }.map { user ->
            _userLocation.value = user.fullAddress
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
            getProducts()
        }.launchIn(viewModelScope, ioDispatcher)
    }

    fun getProducts() {
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
        }.launchIn(viewModelScope, ioDispatcher)
    }

    fun getFavorites() {
        fetchFavoritesUseCase().onEach { favorites ->
            _favoriteList.value = favorites
            _homeUiState.emit(HomeUiState.Success)
        }.catch { exception ->
            _homeUiState.emit(HomeUiState.Error(exception.message))
        }
        .launchIn(viewModelScope, ioDispatcher)
    }
}