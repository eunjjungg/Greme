package com.shootit.greme.network

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.shootit.greme.BuildConfig

class NaverLoginManager(val context: Context) {
    lateinit var oAuthLoginCallback: OAuthLoginCallback
        private set

    private fun naverSetOAuthLoginCallback(updateSocialToken: (String) -> Unit): OAuthLoginCallback {
        oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Log.d("login error", "$message OAuthLoginCallback error code : $errorCode")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d("login error", "$message OAuthLoginCallback failure code : $httpStatus")
            }

            override fun onSuccess() {
                updateSocialToken(NaverIdLoginSDK.getAccessToken() ?: "null")
            }
        }

        return oAuthLoginCallback
    }

    fun startNaverLogin(updateSocialToken: (String) -> Unit) {
        // naver login init
        NaverIdLoginSDK.initialize(
            context = context,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET,
            CLIENT_NAME
        )

        // naver login 진행
        NaverIdLoginSDK.authenticate(
            context = context,
            naverSetOAuthLoginCallback{ updateSocialToken(it) }
        )
    }

    companion object {
        private const val CLIENT_NAME = "Greme"
    }
}