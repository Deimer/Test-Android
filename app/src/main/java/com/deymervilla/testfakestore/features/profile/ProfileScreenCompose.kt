package com.deymervilla.testfakestore.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.deymervilla.repository.models.UserModel
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.features.alerts.ConnectionErrorScreenCompose
import com.deymervilla.testfakestore.features.alerts.ErrorDetailCompose
import com.deymervilla.testfakestore.features.alerts.LoadingScreenCompose
import com.deymervilla.testfakestore.presentation.components.InputCompose
import com.deymervilla.testfakestore.presentation.components.ProfileToolbarCompose
import com.deymervilla.testfakestore.presentation.theme.FakeStoreTheme

private data class FieldConfig(
    val field: EditableField,
    val labelRes: Int,
    val keyboardType: KeyboardType
)

@Composable
fun ProfileScreenCompose(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    attributes: ProfileScreenAttributes
) {
    val uiState by viewModel.profileUiState.collectAsState()
    val userModel by viewModel.userModel.collectAsState()
    val savedUserModel by viewModel.savedUserModel.collectAsState()
    val savingField by viewModel.savingField.collectAsState()
    val failedField by viewModel.failedField.collectAsState()

    when(uiState) {
        is ProfileUiState.Success,
        is ProfileUiState.LoadingUpdate,
        is ProfileUiState.SuccessUpdate -> BodyCompose(
            userModel = userModel,
            savedUserModel = savedUserModel,
            savingField = savingField,
            failedField = failedField,
            onBackClick = { attributes.actions.onPrimaryAction.invoke() },
            onValueChange = viewModel::onFieldValueChange,
            onSaveField = viewModel::updateUser
        )
        is ProfileUiState.Loading -> LoadingScreenCompose()
        is ProfileUiState.ConnectionError -> ConnectionErrorScreenCompose()
        is ProfileUiState.Error -> {
            val errorMessage = (uiState as ProfileUiState.Error).message
            ErrorDetailCompose(errorMessage, attributes.snackbarHostState)
        }
    }
}

@Composable
private fun BodyCompose(
    userModel: UserModel,
    savedUserModel: UserModel,
    savingField: EditableField?,
    failedField: EditableField?,
    onBackClick: () -> Unit,
    onValueChange: (EditableField, String) -> Unit,
    onSaveField: (EditableField) -> Unit,
) {
    val scrollState = rememberScrollState()
    val fieldConfigs = listOf(
        FieldConfig(EditableField.FirstName, R.string.first_name, KeyboardType.Text),
        FieldConfig(EditableField.LastName, R.string.last_name, KeyboardType.Text),
        FieldConfig(EditableField.Email, R.string.email, KeyboardType.Email),
        FieldConfig(EditableField.Phone, R.string.phone, KeyboardType.Phone)
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .imePadding()
    ) {
        ProfileToolbarCompose(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.dimen_16)),
            title = stringResource(R.string.your_profile),
            imageUrl = userModel.imageUrl,
            fullName = userModel.fullName,
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_12)))
        fieldConfigs.forEach { config ->
            val currentValue = when (config.field) {
                EditableField.FirstName -> userModel.firstName
                EditableField.LastName -> userModel.lastName
                EditableField.Email -> userModel.email
                EditableField.Phone -> userModel.phone
            }

            val originalValue = when (config.field) {
                EditableField.FirstName -> savedUserModel.firstName
                EditableField.LastName -> savedUserModel.lastName
                EditableField.Email -> savedUserModel.email
                EditableField.Phone -> savedUserModel.phone
            }

            InputCompose(
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dimen_16)),
                label = stringResource(config.labelRes),
                value = currentValue,
                originalValue = originalValue,
                isLoading = savingField == config.field,
                errorState = failedField == config.field,
                keyboardType = config.keyboardType,
                onValueChange = { newValue -> onValueChange(config.field, newValue) },
                onActionClick = { onSaveField(config.field) }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_12)))
        }

        InputCompose(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dimen_16)),
            label = stringResource(R.string.full_address),
            value = userModel.fullAddress,
            originalValue = userModel.fullAddress,
            enabled = false,
            actionText = stringResource(R.string.change),
            onValueChange = {},
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true, name = "1. Default - sin cambios")
@Composable
private fun BodyComposeDefaultPreview() {
    val sampleSavedUser = UserModel(
        id = 1,
        username = "jdoe",
        email = "john.doe@example.com",
        phone = "+1 555 1234",
        firstName = "John",
        lastName = "Doe",
        city = "Springfield",
        street = "Main Street",
        number = 123,
        zipcode = "12345",
        latitude = "40.7128",
        longitude = "-74.0060",
    )
    FakeStoreTheme {
        BodyCompose(
            userModel = sampleSavedUser,
            savedUserModel = sampleSavedUser,
            savingField = null,
            failedField = null,
            onBackClick = {},
            onValueChange = { _, _ -> },
            onSaveField = {}
        )
    }
}