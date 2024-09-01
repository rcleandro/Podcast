package br.com.leandro.podcast.utils

import java.util.Locale

fun Int.toDurationTime(): String {
    if (this < 0) return "00:00"

    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return if (hours > 0) {
        String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}