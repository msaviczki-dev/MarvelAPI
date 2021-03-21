package com.msaviczki.marvel_api.network

import retrofit2.Retrofit

private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

class ApiClient {
    val client: Retrofit by lazy {
        ApiBuilder(BASE_URL).client
    }
}