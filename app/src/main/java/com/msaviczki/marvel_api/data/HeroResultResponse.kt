package com.msaviczki.marvel_api.data

import com.google.gson.annotations.SerializedName

data class HeroResultResponse(
    @SerializedName("results")
    val resultList: MutableList<HeroResponse>
)