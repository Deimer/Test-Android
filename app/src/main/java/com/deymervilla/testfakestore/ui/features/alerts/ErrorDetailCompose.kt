package com.deymervilla.testfakestore.ui.features.alerts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme
import kotlinx.coroutines.launch

@Composable
fun ErrorDetailCompose(
    errorMessage: String? = null,
    snackbarHostState: SnackbarHostState
) {
    val snackbarScope = rememberCoroutineScope()
    val message = stringResource(R.string.error_generic)
    LaunchedEffect(Unit) {
        snackbarScope.launch {
            val message = errorMessage ?: message
            snackbarHostState.showSnackbar(message = message)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorDetailComposePreview() {
    FakeStoreTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                ErrorDetailCompose(
                    errorMessage = "Test message for error details",
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}