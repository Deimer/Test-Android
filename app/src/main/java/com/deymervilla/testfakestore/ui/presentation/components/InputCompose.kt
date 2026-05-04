package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.DarkSurface
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed
import com.deymervilla.testfakestore.ui.presentation.theme.LightGray

@Composable
fun InputCompose(
    label: String,
    value: String,
    originalValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorState: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    actionText: String = stringResource(R.string.change),
    retryText: String = stringResource(R.string.retry),
    onActionClick: () -> Unit
) {
    val isChanged = value != originalValue
    val showActionButton = isChanged || errorState
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
            enabled = enabled,
            shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_12)),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if(errorState) Color.Red else DarkSurface,
                unfocusedBorderColor = if(errorState) Color.Red else Color.LightGray,
                disabledContainerColor = LightGray
            ),
            trailingIcon = {
                if (showActionButton) {
                    Text(
                        text = displayActionText,
                        color = ForeverRed,
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.dimen_12))
                            .clickable { onActionClick() },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
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