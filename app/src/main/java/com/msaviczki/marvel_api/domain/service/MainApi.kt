package com.msaviczki.marvel_api.domain.service

import com.msaviczki.marvel_api.data.HeroResultResponse
import com.msaviczki.marvel_api.data.ResponseBody
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("characters")
    fun requestHeroesList(
        @Query("orderBy") order: String = "-modified",
        @Query("limit") limit: Int = 100
    ): Single<ResponseBody<HeroResultResponse>>
}