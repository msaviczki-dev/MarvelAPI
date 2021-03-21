package com.msaviczki.marvel_api.domain.service

import com.msaviczki.marvel_api.data.HeroResultResponse
import com.msaviczki.marvel_api.data.ResponseBody
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroDetailApi {
    @GET("characters/{id}")
    fun requestHeroDetail(@Path("id") id: Int): Single<ResponseBody<HeroResultResponse>>
}