package br.com.leandro.podcast.utils

/**
 * Convert a String to a valid URL.
 *
 * @return a valid URL.
 */
fun String.toUrl(): String {
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
