package com.msaviczki.marvel_api.network

import com.msaviczki.marvel_api.helper.extension.toMd5
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val TIMESTAMP = "ts"
private const val API_KEY = "apikey"
private const val HASH = "hash"
private const val TIME_ZONE_ID = "UTC"
private const val MILLISECONDS = 1000L
private const val TIMEOUT_TIME = 45L
private const val PUBLIC_KEY_API_VALUE = "d979655b38fcd109bc94848f7b022418"
private const val PRIVATE_KEY_API_VALUE = "bf63b65b4874863bf56c06de794f82add5f77fe0"

class ApiBuilder(private val url: String) {
    val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val okHttpBuilder by lazy {
        OkHttpClient.Builder()
            .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val timeStamp =
                    (Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE_ID)).timeInMillis / MILLISECONDS).toString()
                val hashValue = "$timeStamp$PRIVATE_KEY_API_VALUE$PUBLIC_KEY_API_VALUE".toMd5()
                val original = chain.request()
                val urlDefault = original.url()
                val url = urlDefault.newBuilder()
                    .addQueryParameter(API_KEY, PUBLIC_KEY_API_VALUE)
                    .addQueryParameter(TIMESTAMP, timeStamp)
                    .addQueryParameter(HASH, hashValue)
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
    }
}