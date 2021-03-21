package com.msaviczki.marvel_api.data

import com.google.gson.annotations.SerializedName

data class ResponseBody<DATA>(
    @SerializedName("data")
    var data: DATA?,
    @SerializedName("code")
    var code: String
)