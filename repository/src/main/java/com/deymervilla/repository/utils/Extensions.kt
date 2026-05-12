package com.deymervilla.repository.utils

import java.util.Locale

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Float?.orZero(): Float = this ?: 0f

fun String?.capitalizeWords(): String =
    this?.split(" ")?.joinToString(" ") { word ->
        word.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.getDefault())
            else char.toString()
        }
    } ?: ""