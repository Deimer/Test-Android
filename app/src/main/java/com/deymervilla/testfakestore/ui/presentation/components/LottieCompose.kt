package com.deymervilla.testfakestore.ui.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deymervilla.testfakestore.R
import com.deymervilla.testfakestore.ui.presentation.theme.FakeStoreTheme

@Composable
fun LottieCompose(
    rawRes: Int,
    modifier: Modifier = Modifier,
    size: Dp = dimensionResource(id = R.dimen.dimen_100),
    iterations: Int = 3,
    speed: Float = 2f,
    autoPlay: Boolean = true,
    onAnimationEnd: () -> Unit = {}
) {
    val isPlaying by remember {
        mutableStateOf(autoPlay)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(rawRes)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = iterations,
        speed = speed
    )
    LottieAnimation(
        modifier = modifier.size(size),
        composition = composition,
        progress = { progress },
    )
    LaunchedEffect(key1 = progress) {
        if (progress == 1f) {
            onAnimationEnd.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LottieComposePreview() {
    FakeStoreTheme {
        LottieCompose(
            rawRes = R.raw.ic_welcome,
            modifier = Modifier
        )
    }
}