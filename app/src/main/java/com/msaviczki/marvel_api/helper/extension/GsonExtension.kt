package com.msaviczki.marvel_api.helper.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified DATA : MutableList<*>> Gson.fromJsonList(json: String): DATA {
    val type = object : TypeToken<DATA>() {}.type
    return fromJson(json, type)
}