package com.deymervilla.testfakestore.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deymervilla.repository.models.UserModel
import com.deymervilla.testfakestore.di.IoDispatcher
import com.deymervilla.testfakestore.utils.default
import com.deymervilla.testfakestore.utils.failure
import com.deymervilla.testfakestore.utils.isIOEx
import com.deymervilla.testfakestore.utils.launchIn
import com.deymervilla.testfakestore.utils.map
import com.deymervilla.testfakestore.utils.start
import com.deymervilla.testfakestore.utils.success
import com.deymervilla.usecase.user.FetchUserUseCase
import com.deymervilla.usecase.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class ProfileUiState {
    data object Loading: ProfileUiState()
    data object LoadingUpdate: ProfileUiState()
    data object Success: ProfileUiState()
    data object SuccessUpdate: ProfileUiState()
    data object ConnectionError: ProfileUiState()
    data class Error(val message: String? = null): ProfileUiState()
}

enum class EditableField { FirstName, LastName, Email, Phone }

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

    private val _savedUserModel = MutableStateFlow(UserModel())
    val savedUserModel: StateFlow<UserModel> = _savedUserModel.asStateFlow()

    private val _savingField = MutableStateFlow<EditableField?>(null)
    val savingField: StateFlow<EditableField?> = _savingField.asStateFlow()

    private val _failedField = MutableStateFlow<EditableField?>(null)
    val failedField: StateFlow<EditableField?> = _failedField.asStateFlow()

    init { fetchUser() }

    private fun fetchUser() {
        fetchUserUseCase(8).start {
            _profileUiState.emit(ProfileUiState.Loading)
        }.map { user ->
            _userModel.value = user
            _savedUserModel.value = user
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

    fun onFieldValueChange(field: EditableField, newValue: String) {
        _userModel.update { user ->
            when (field) {
                EditableField.FirstName -> user.copy(firstName = newValue)
                EditableField.LastName -> user.copy(lastName = newValue)
                EditableField.Email -> user.copy(email = newValue)
                EditableField.Phone -> user.copy(phone = newValue)
            }
        }
        if (_failedField.value == field) _failedField.value = null
    }

    fun updateUser(field: EditableField) {
        _savingField.value = field
        _failedField.value = null
        updateUserUseCase(_userModel.value).start {
            _profileUiState.emit(ProfileUiState.LoadingUpdate)
        }.success {
            _savedUserModel.value = _userModel.value
            _savingField.value = null
            _profileUiState.emit(ProfileUiState.SuccessUpdate)
        }.failure { exception ->
            _profileUiState.emit(ProfileUiState.Error(exception.message.orEmpty()))
        }.launchIn(viewModelScope, ioDispatcher)
    }
}