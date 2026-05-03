package com.deymervilla.testfakestore.domain.utils

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Float?.orZero(): Float = this ?: 0f