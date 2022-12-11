package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityLoginBinding
import com.shootit.greme.databinding.ActivityMainBinding
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.network.KakaoLoginManager
import com.shootit.greme.network.NaverLoginManager
import com.shootit.greme.repository.LoginRepository
import com.shootit.greme.util.EncryptedSpfImpl
import com.shootit.greme.util.EncryptedSpfObject
import com.shootit.greme.viewmodel.LoginViewModel
import com.shootit.greme.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override val viewModel by lazy {
        ViewModelProvider(
            this, LoginViewModel.LoginViewModelFactory(LoginRepository.getInstance(application)!!)
        ).get(LoginViewModel::class.java)
    }
    val NAVER_LOGIN_DOMAIN = "naver"
    val KAKAO_LOGIN_DOMAIN = "kakao"

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@LoginActivity
        binding.viewModel = this.viewModel
    }

    override fun onCreateAction() {
        binding.btnLoginNaver.setOnClickListener {
            testNaverLogin()
        }

        binding.btnLoginKakao.setOnClickListener {
            testKakaoLogin()
        }

        binding.btnSpf.setOnClickListener {
            val tmp = EncryptedSpfImpl(EncryptedSpfObject.getEncryptedSpf(this)).getAccessToken()
            Log.d("login ccheck token", tmp.toString())
        }
    }

    private fun testNaverLogin() {
        NaverLoginManager(context = this).startNaverLogin {
            ConnectionObject.token = it
            viewModel.getLoginData(NAVER_LOGIN_DOMAIN)
        }
    }

    private fun testKakaoLogin() {
        KakaoLoginManager(context = this).startKakaoLogin {
            EncryptedSpfImpl(EncryptedSpfObject.getEncryptedSpf(this)).setAccessToken(
                it
            )
            ConnectionObject.token = it
            viewModel.getLoginData(KAKAO_LOGIN_DOMAIN)
        }
    }
}