package com.msaviczki.marvel_api.domain.repository

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.data.HeroResponse
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource
import com.msaviczki.marvel_api.domain.map.map
import com.msaviczki.marvel_api.domain.service.MainApi
import io.reactivex.Single

class MainRepositoryImpl(
    private val api: MainApi
) : MainRepository {
    override fun requestHeroesList(): Single<MutableList<HeroItem>> {
        return api.requestHeroesList()
            .map { result ->
                result.data?.resultList?.map()
            }
    }
}