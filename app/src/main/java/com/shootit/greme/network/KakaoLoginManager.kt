package com.shootit.greme.network

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager(val context: Context) {

    private lateinit var kakaoLoginState: KakaoLoginState
    private lateinit var kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit

    fun startKakaoLogin(
        updateSocialToken: (String) -> Unit
    ) {
        kakaoLoginState = getKakaoLoginState()
        kakaoLoginCallback = getLoginCallback(updateSocialToken)

        when (kakaoLoginState) {
            KakaoLoginState.KAKAO_APP_LOGIN -> {
                onKakaoAppLogin(updateSocialToken)
            }
            KakaoLoginState.KAKAO_WEB_LOGIN -> {
                onKakaoWebLogin(updateSocialToken)
            }
        }
    }

    private fun getKakaoLoginState(): KakaoLoginState =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
            KakaoLoginState.KAKAO_APP_LOGIN
        } else {
            KakaoLoginState.KAKAO_WEB_LOGIN
        }

    private fun getLoginCallback(updateSocialToken: (String) -> Unit): (OAuthToken?, Throwable?) -> Unit {
        return { oAuthToken, throwable ->
            if (throwable != null) {
                Log.d("kakao login Error", throwable.stackTraceToString())
            } else if (oAuthToken != null) {
                updateSocialToken(oAuthToken.accessToken)
            }
        }
    }

    private fun isAgreementChecked() {

        var isChecked = true
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(
                    "kakao login error",
                    "kakao user request failed : ${error.stackTraceToString()}"
                )
            } else if (user != null) {
                var scopes = mutableListOf<String>()
                if (user.kakaoAccount?.emailNeedsAgreement == true) {
                    scopes.add("account_email")
                }

                if (scopes.count() > 0) {
                    isChecked = !isChecked
                    Log.d("kakao login", "isAgreementChecked()")
                    disconnectKakaoLogin()
                }
            }
        }

    }

    private fun disconnectKakaoLogin() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.d("kakao login disconnect", "kakao user disconnect failed")
            } else {
                Log.d("kakao login disconnect", "kakao user disconnect completed")
            }
        }
    }

    private fun onKakaoAppLogin(updateSocialToken: (String) -> Unit) {
        UserApiClient.instance.loginWithKakaoTalk(context = context) { token: OAuthToken?, error: Throwable? ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                onKakaoWebLogin(updateSocialToken)
            } else if (token != null) {
                isAgreementChecked()
                updateSocialToken(token.accessToken)
            }
        }
    }

    private fun onKakaoWebLogin(updateSocialToken: (String) -> Unit) {
        UserApiClient.instance.loginWithKakaoAccount(
            context = context,
        ) { token: OAuthToken?, error: Throwable? ->
            if (error != null) {
                Log.d("kakao login web", error.stackTraceToString())
            } else if (token != null) {
                isAgreementChecked()
                updateSocialToken(token.accessToken)
            }
        }
    }
}

enum class KakaoLoginState() {
    KAKAO_APP_LOGIN, KAKAO_WEB_LOGIN
}