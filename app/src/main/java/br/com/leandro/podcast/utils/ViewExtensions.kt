package br.com.leandro.podcast.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hides the keyboard.
 *
 * @receiver The view that has the keyboard.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}