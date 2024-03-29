package com.shootit.greme.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.model.ApplicationContextInfo
import com.shootit.greme.util.EncryptedSpfImpl
import com.shootit.greme.util.EncryptedSpfObject
import com.shootit.greme.util.GremeApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionObject {
    private const val BASE_URL = "http://13.125.79.233:8081"
    var token: String = EncryptedSpfImpl(EncryptedSpfObject.getEncryptedSpf(GremeApplication.instance.applicationContext)).getAccessToken().toString()
    //이후에 서버에서 내려주는 토큰 값으로 변경 필요
    var email: String = ""

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

    val getConnectionRetrofitService: ConnectionInterface by lazy {
        getRetrofit.create(ConnectionInterface::class.java)
    }

    val getSignUpRetrofitService: SignUpInterface by lazy {
        getRetrofit.create(SignUpInterface::class.java)
    }

    val getChallengeRetrofitService: ChallengeInterface by lazy {
        getRetrofit.create(ChallengeInterface::class.java)
    }

    val getDiaryWriteRetrofitService: DiaryWriteInterface by lazy {
        getRetrofit.create(DiaryWriteInterface::class.java)
    }

    val getDiarySearchRetrofitService: SearchInterface by lazy {
        getRetrofit.create(SearchInterface::class.java)
    }

    val getSignOutRetrofitService: SignOutInterface by lazy {
        getRetrofit.create(SignOutInterface::class.java)
    }

    val getSettingRetrofitService: SettingInterface by lazy {
        getRetrofit.create(SettingInterface::class.java)
    }

    val getOtherUserProfileRetrofitService: OtherUserProfileInterface by lazy {
        getRetrofit.create(OtherUserProfileInterface::class.java)
    }
}