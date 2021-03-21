package com.msaviczki.marvel_api.helper.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

inline fun <reified T : Any> FragmentActivity.launch(
    requestCode: Int = -1,
    options: Bundle? = null,
    needClear: Boolean = false,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = intent<T>(this)
    if (needClear) {
        finish()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> intent(context: Context): Intent = Intent(context, T::class.java)