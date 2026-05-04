package com.deymervilla.testfakestore.ui.features.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.components.LottieCompose
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme

@Composable
fun SplashScreenCompose(
    actions: SplashScreenActions
) {
    BodyContent(actions)
}

@Composable
private fun BodyContent(
    actions: SplashScreenActions
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = dimensionResource(R.dimen.dimen_12)
        ),
        contentAlignment = Alignment.Center
    ) {
        LottieCompose(
            rawRes = R.raw.ic_welcome,
            size = dimensionResource(id = R.dimen.dimen_180),
            iterations = 3,
            onAnimationEnd = { actions.onPrimaryAction.invoke() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.dimen_80)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.title_welcome),
                style = MaterialTheme.typography.headlineLarge.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = stringResource(R.string.legend_welcome),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.dimen_8))
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    FakeStoreTheme {
        Scaffold {
            SplashScreenCompose(
                actions = SplashScreenActions(
                    onPrimaryAction = {},
                )
            )
        }
    }
}