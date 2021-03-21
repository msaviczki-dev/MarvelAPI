package com.msaviczki.marvel_api.domain.repository

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.domain.map.map
import com.msaviczki.marvel_api.domain.service.HeroDetailApi
import io.reactivex.Single

class HeroDetailRepositoryImpl(private val api: HeroDetailApi) : HeroDetailRepository {
    override fun requestHeroDetail(id: Int): Single<MutableList<HeroItem>> {
        return api.requestHeroDetail(id).map { response ->
            response.data?.resultList?.map()
        }
    }
}