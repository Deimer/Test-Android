package com.deymervilla.testfakestore.features.alerts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieConstants
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.presentation.components.LottieCompose
import com.deymervilla.testfakestore.presentation.theme.FakeStoreTheme

@Composable
fun LoadingScreenCompose() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieCompose(
            rawRes = R.raw.ic_loading,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dimen_20)),
            size = dimensionResource(id = R.dimen.dimen_160),
            iterations = LottieConstants.IterateForever
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun LoadingScreenComposePreview() {
    FakeStoreTheme {
        Scaffold {
            LoadingScreenCompose()
        }
    }
}