package com.shootit.greme.util

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.shootit.greme.BuildConfig

class GremeApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    companion object {
        lateinit var instance: GremeApplication
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }
}