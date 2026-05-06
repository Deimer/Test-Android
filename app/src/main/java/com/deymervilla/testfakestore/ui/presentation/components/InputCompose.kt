package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed
import com.deymervilla.testfakestore.ui.presentation.theme.LightGray
import com.deymervilla.testfakestore.ui.presentation.theme.MidGray

@Composable
fun InputCompose(
    label: String,
    value: String,
    originalValue: String,
    onValueChange: (String) -> Unit,
    onActionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorState: Boolean = false,
    isLoading: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    actionText: String = stringResource(R.string.change),
    retryText: String = stringResource(R.string.retry),
) {
    val isChanged = value != originalValue
    val showActionButton = (isChanged || errorState) && !isLoading
    val displayActionText = if (errorState) retryText else actionText

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.dimen_8))
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled && !isLoading,
            shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = KeyboardCapitalization.Words
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (errorState) Color.Red else MidGray,
                unfocusedBorderColor = if (errorState) Color.Red else Color.LightGray,
                disabledContainerColor = LightGray
            ),
            trailingIcon = {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.dimen_20))
                                .padding(end = dimensionResource(R.dimen.dimen_8)),
                            strokeWidth = dimensionResource(R.dimen.dimen_2),
                            color = ForeverRed,
                        )
                    }
                    showActionButton -> {
                        Text(
                            text = displayActionText,
                            color = ForeverRed,
                            modifier = Modifier
                                .padding(end = dimensionResource(R.dimen.dimen_12))
                                .clickable { onActionClick(value) },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputComposePreview() {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.dimen_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_20))
    ) {
        InputCompose(
            label = "Phone Number (Initial)",
            value = "1-478-001-0890",
            originalValue = "1-478-001-0890",
            onValueChange = {},
            onActionClick = {}
        )
        InputCompose(
            label = "Phone Number (Edited)",
            value = "1-478-001-9999",
            originalValue = "1-478-001-0890",
            actionText = "Update",
            onValueChange = {},
            onActionClick = {}
        )
        InputCompose(
            label = "Phone Number (Error)",
            value = "1-478-001-9999",
            originalValue = "1-478-001-0890",
            errorState = true,
            retryText = "Try Again",
            onValueChange = {},
            onActionClick = {}
        )
    }
}