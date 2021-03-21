package com.msaviczki.marvel_api.data

import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
)