package com.msaviczki.marvel_api.domain.usecase

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource
import com.msaviczki.marvel_api.domain.map.map
import com.msaviczki.marvel_api.domain.repository.MainRepository
import io.reactivex.Single

class MainUseCase(
    private val repository: MainRepository,
    private val dataSource: FavoriteHeroesDataSource
) {
    operator fun invoke(): Single<MutableList<HeroItem>> {
        return repository.requestHeroesList().map { item ->
            item.map(dataSource.get() ?: mutableListOf())
        }
    }
}