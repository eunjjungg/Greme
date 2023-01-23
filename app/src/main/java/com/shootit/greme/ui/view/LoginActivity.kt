package com.shootit.greme.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        setBtnListener()
    }


    private fun setBtnListener() {
        val callback = object : LoginFinishInterface {
            override fun openActivityCallback(isExistingUser: Boolean) {
                Toast.makeText(
                    this@LoginActivity, "로그인 완료", Toast.LENGTH_SHORT
                ).show()
                if (isExistingUser) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                }
                finish()
            }

            override fun errorCallback() {
                Toast.makeText(
                    this@LoginActivity, "로그인하는데 문제가 발생했습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnLoginNaver.setOnClickListener {
            NaverLoginManager(context = this@LoginActivity).startNaverLogin {
                ConnectionObject.token = it
                Log.d("ccheck", ConnectionObject.token)
                viewModel.getLoginData(
                    NAVER_LOGIN_DOMAIN,
                    EncryptedSpfObject.getEncryptedSpf(this@LoginActivity),
                    callback
                )
            }
        }

        binding.btnLoginKakao.setOnClickListener {
            KakaoLoginManager(context = this@LoginActivity).startKakaoLogin {
                ConnectionObject.token = it
                Log.d("ccheck tokent", it)
                viewModel.getLoginData(
                    KAKAO_LOGIN_DOMAIN,
                    EncryptedSpfObject.getEncryptedSpf(this@LoginActivity),
                    callback
                )
            }
        }
    }


}