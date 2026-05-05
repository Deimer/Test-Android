package com.deymervilla.testfakestore.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.domain.usecases.user.FetchUserUseCase
import com.deymervilla.testfakestore.domain.usecases.user.UpdateUserUseCase
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
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class ProfileUiState {
    data object Loading: ProfileUiState()
    data object Success: ProfileUiState()
    data object ConnectionError: ProfileUiState()
    data class Error(val message: String? = null): ProfileUiState()
}

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fetchUserUseCase: FetchUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
): ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState = _profileUiState.asStateFlow()

    private val _userModel = MutableStateFlow(UserModel())
    val userModel: StateFlow<UserModel> = _userModel.asStateFlow()

    init { fetchUser() }

    fun fetchUser() {
        fetchUserUseCase(8).start {
            _profileUiState.emit(ProfileUiState.Loading)
        }.map { user ->
            _userModel.value = user
        }.success {
            _profileUiState.emit(ProfileUiState.Success)
        }.failure { exception ->
            exception.isIOEx {
                _profileUiState.emit(ProfileUiState.ConnectionError)
            }
            exception.default {
                _profileUiState.emit(ProfileUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }

    fun updateUser(
        firstName: String,
        lastName: String,
        phone: String
    ) {
        _userModel.update {
            it.copy(
                firstName = firstName,
                lastName = lastName,
                phone = phone
            )
        }
        updateUserUseCase(_userModel.value).start {
            _profileUiState.emit(ProfileUiState.Loading)
        }.map { user ->
            user
        }.success {
            _profileUiState.emit(ProfileUiState.Success)
        }.failure { exception ->
            exception.isIOEx {
                _profileUiState.emit(ProfileUiState.ConnectionError)
            }
            exception.default {
                _profileUiState.emit(ProfileUiState.Error(exception.message.orEmpty()))
            }
        }.launchIn(viewModelScope, ioDispatcher)
    }
}