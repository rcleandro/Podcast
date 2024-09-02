package br.com.leandro.podcast.utils

import android.text.Html
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Convert a String to a valid URL.
 *
 * @return a valid URL.
 */
fun String.toRssUrl(): String {
    var result = this.trim()

    if (result.startsWith("http://", ignoreCase = true)) {
        result = result.replaceFirst("http://", "https://", ignoreCase = true)
    }

    if (!result.startsWith("https://", ignoreCase = true)) {
        result = "https://$result"
    }

    if (!result.endsWith("/")) {
        result = "$result/"
    }

    return result
}

/**
 * Convert a HTML text to a String.
 *
 * @return a String.
 */
fun String.htmlTextToString(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
}

/**
 * Convert a String to a date.
 *
 * @return a date.
 */
fun String.toDateString(): String {
    val formatCurrent = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    try {
        val date = formatCurrent.parse(this)
        return date?.let { formatDate.format(it) } ?: ""
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""
}
