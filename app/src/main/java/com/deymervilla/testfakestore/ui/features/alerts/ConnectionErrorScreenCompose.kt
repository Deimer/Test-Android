package com.deymervilla.testfakestore.ui.features.alerts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme
import com.deymervilla.testfakestore.ui.presentation.theme.ForeverRed

@Composable
fun ConnectionErrorScreenCompose(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_120)),
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(id = R.string.connection_error),
            tint = ForeverRed
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16)))
        Text(
            text = stringResource(id = R.string.connection_error_message),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_24)))
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun ConnectionErrorScreenPreview() {
    FakeStoreTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            ConnectionErrorScreenCompose()
        }
    }
}