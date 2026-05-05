package com.deymervilla.testfakestore.ui.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.ui.features.alerts.ConnectionErrorScreenCompose
import com.deymervilla.testfakestore.ui.features.alerts.ErrorDetailCompose
import com.deymervilla.testfakestore.ui.features.alerts.LoadingScreenCompose
import com.deymervilla.testfakestore.ui.presentation.components.InputCompose
import com.deymervilla.testfakestore.ui.presentation.components.ProfileToolbarCompose
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme

@Composable
fun ProfileScreenCompose(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    attributes: ProfileScreenAttributes
) {
    val uiState by viewModel.profileUiState.collectAsState()
    val userModel by viewModel.userModel.collectAsState()

    when(uiState) {
        is ProfileUiState.Success -> BodyCompose(
            actions = attributes.actions,
            userModel = userModel
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
    actions: ProfileScreenActions,
    userModel: UserModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ProfileToolbarCompose(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.dimen_16)),
            title = stringResource(R.string.your_profile),
            imageUrl = "https://i.pravatar.cc/300",
            fullName = userModel.fullName,
            onBackClick = { actions.onPrimaryAction.invoke() }
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.dimen_12)))
        InputCompose(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dimen_16)),
            label = stringResource(R.string.full_name),
            value = userModel.firstName,
            originalValue = userModel.firstName,
            keyboardType = KeyboardType.Text,
            actionText = stringResource(R.string.change),
            onValueChange = {},
            onActionClick = {}
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.dimen_12)))
        InputCompose(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dimen_16)),
            label = stringResource(R.string.full_name),
            value = userModel.lastName,
            originalValue = userModel.lastName,
            keyboardType = KeyboardType.Text,
            actionText = stringResource(R.string.change),
            onValueChange = {},
            onActionClick = {}
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.dimen_12)))
        InputCompose(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dimen_16)),
            label = stringResource(R.string.email),
            value = userModel.email,
            originalValue = userModel.email,
            keyboardType = KeyboardType.Email,
            actionText = stringResource(R.string.change),
            onValueChange = {},
            onActionClick = {}
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.dimen_12)))
        InputCompose(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.dimen_16)),
            label = stringResource(R.string.phone),
            value = userModel.phone,
            originalValue = userModel.phone,
            keyboardType = KeyboardType.Phone,
            actionText = stringResource(R.string.change),
            onValueChange = {},
            onActionClick = {}
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.dimen_12)))
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProfileScreenSuccessPreview() {
    FakeStoreTheme {
        BodyCompose(
            actions = ProfileScreenActions(
                onPrimaryAction = {}
            ),
            userModel = UserModel(
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
                longitude = "-74.0060"
            )
        )
    }
}