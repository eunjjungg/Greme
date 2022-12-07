package com.shootit.greme.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ConnectionObject {
    private const val BASE_URL = "http://13.125.79.233:8081"
    var token: String = ""

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    // header 추가
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    ).addInterceptor {
        val request = it.request()
            .newBuilder()
            .addHeader("accessToken", "$token")
            .build()

        val response = it.proceed(request)
        response
    }.build()

    private val getRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val getRetrofitService: ConnectionInterface by lazy {
        getRetrofit.create(ConnectionInterface::class.java)
    }
}