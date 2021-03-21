package com.msaviczki.marvel_api.domain.usecase

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource
import com.msaviczki.marvel_api.domain.map.map
import com.msaviczki.marvel_api.domain.repository.HeroDetailRepository
import io.reactivex.Single

class HeroDetailUseCase(
    private val repository: HeroDetailRepository,
    private val dataSource: FavoriteHeroesDataSource
) {
    operator fun invoke(id: Int): Single<HeroItem> {
        return repository.requestHeroDetail(id).map { data ->
            data.map(dataSource.get() ?: mutableListOf()).first()
        }
    }
}