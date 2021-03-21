package com.msaviczki.marvel_api.presentation.`interface`

import com.msaviczki.marvel_api.data.HeroItem

interface FavoriteClickListener {
    fun favoriteOnClickListener(state: Boolean, item: HeroItem)
}