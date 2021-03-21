package com.msaviczki.marvel_api.domain.usecase

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource

class GetFavoriteUseCase(private val dataSource: FavoriteHeroesDataSource) {
    operator fun invoke(): MutableList<HeroItem> = dataSource.get() ?: mutableListOf()
}