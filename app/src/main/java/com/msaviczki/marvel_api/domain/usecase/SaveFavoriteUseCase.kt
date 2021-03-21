package com.msaviczki.marvel_api.domain.usecase

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource

class SaveFavoriteUseCase(private val dataSource: FavoriteHeroesDataSource) {
    operator fun invoke(state: Boolean, item: HeroItem): Boolean {
        if (state) {
            dataSource.save(mutableListOf(item))
        } else {
            return dataSource.remove(mutableListOf(item))
        }
        return true
    }
}