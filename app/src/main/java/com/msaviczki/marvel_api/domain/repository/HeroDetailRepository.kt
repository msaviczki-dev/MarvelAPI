package com.msaviczki.marvel_api.domain.repository

import com.msaviczki.marvel_api.data.HeroItem
import io.reactivex.Single
import retrofit2.http.Query

interface HeroDetailRepository {
    fun requestHeroDetail(@Query("characterId") id: Int): Single<MutableList<HeroItem>>
}