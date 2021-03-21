package com.msaviczki.marvel_api.data

data class HeroItem(
    val id: String,
    val name: String,
    val urlImage: String,
    val decription: String,
    var isFavorite: Boolean = false
)