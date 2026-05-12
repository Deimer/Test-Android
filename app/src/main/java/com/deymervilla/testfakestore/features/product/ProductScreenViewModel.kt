package com.deymervilla.testfakestore.features.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deymervilla.repository.models.ProductModel
import com.deymervilla.testfakestore.di.IoDispatcher
import com.deymervilla.testfakestore.utils.failure
import com.deymervilla.testfakestore.utils.launchIn
import com.deymervilla.testfakestore.utils.map
import com.deymervilla.testfakestore.utils.success
import com.deymervilla.usecase.product.FetchProductByIdUseCase
import com.deymervilla.usecase.product.SetProductFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class ProductUiState {
    data object Loading: ProductUiState()
    data object Success: ProductUiState()
    data class Error(val message: String? = null): ProductUiState()
}

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchProductByIdUseCase: FetchProductByIdUseCase,
    private val setProductFavoriteUseCase: SetProductFavoriteUseCase
): ViewModel() {

    private val _productUiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val productUiState = _productUiState.asStateFlow()

    private val _productUiModel = MutableStateFlow(ProductModel())
    val productUiModel: StateFlow<ProductModel> = _productUiModel.asStateFlow()

    fun getProduct(productId: Int) {
        fetchProductByIdUseCase(productId).map { product ->
            _productUiModel.value = product
        }.success {
            _productUiState.emit(ProductUiState.Success)
        }.failure { exception ->
            _productUiState.emit(ProductUiState.Error(exception.message))
        }.launchIn(viewModelScope, ioDispatcher)
    }

    fun setFavorite() {
        val current = _productUiModel.value
        val newFavorite = current.isFavorite.not()
        setProductFavoriteUseCase(
            productId = current.id,
            isFavorite = newFavorite
        ).success {
            _productUiModel.value = current.copy(isFavorite = newFavorite)
        }.failure { exception ->
            _productUiState.emit(ProductUiState.Error(exception.message))
        }.launchIn(viewModelScope, ioDispatcher)
    }
}