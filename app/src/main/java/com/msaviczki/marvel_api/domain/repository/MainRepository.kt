package com.msaviczki.marvel_api.domain.repository

import com.msaviczki.marvel_api.data.HeroItem
import io.reactivex.Single

interface MainRepository {
    fun requestHeroesList(): Single<MutableList<HeroItem>>
}