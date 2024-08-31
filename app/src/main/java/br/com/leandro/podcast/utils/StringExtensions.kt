package br.com.leandro.podcast.utils

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

//    // Remove http:// if it exists
//    if (result.startsWith("http://", ignoreCase = true)) {
//        result = result.substring(7)
//    }
//
//    // Remove https:// if it exists
//    if (result.startsWith("https://", ignoreCase = true)) {
//        result = result.substring(8)
//    }

//    // Remove https:// if end with /
//    if (result.endsWith("/")) {
//        result = result.substring(0, result.length - 1)
//    }

    return result
}
