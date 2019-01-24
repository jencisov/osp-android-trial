package com.jencisov.osp_android_trial.extensions

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.dismissKeyboard(view: View) {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.let { it.hideSoftInputFromWindow(view.windowToken, 0) }
}