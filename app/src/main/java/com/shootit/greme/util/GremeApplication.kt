package com.shootit.greme.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.shootit.greme.BuildConfig

class GremeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}