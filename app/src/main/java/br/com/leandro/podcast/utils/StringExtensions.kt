package br.com.leandro.podcast.utils

import android.text.Html

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

fun String.htmlTextToString(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
}
