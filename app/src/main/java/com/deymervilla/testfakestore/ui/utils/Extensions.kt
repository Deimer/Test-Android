package com.deymervilla.testfakestore.ui.utils

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("DefaultLocale")
fun Int.toKNotation(): String {
    return when {
        this >= 1000000 -> {
            val millions = this / 1000000.0
            if (millions % 1 == 0.0) "${millions.toInt()}M" else "${String.format("%.1f", millions)}M"
        }
        this >= 1000 -> {
            val thousands = this / 1000.0
            if (thousands % 1 == 0.0) "${thousands.toInt()}k" else "${String.format("%.1f", thousands)}k"
        }
        else -> this.toString()
    }
}

fun Double.toUsd(
    showSymbol: Boolean = true,
    decimals: Int = 2
): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = decimals
        maximumFractionDigits = decimals
    }
    val formatted = format.format(this)
    return if (showSymbol) formatted else formatted.replace("$", "").trim()
}

fun Float.toUsd(
    showSymbol: Boolean = true,
    decimals: Int = 2
): String = this.toDouble().toUsd(showSymbol, decimals)

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.getDefault())
            else char.toString()
        }
    }