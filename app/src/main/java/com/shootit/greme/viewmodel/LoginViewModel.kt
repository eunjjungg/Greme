package com.shootit.greme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {

    fun getNaverLoginData() = viewModelScope.launch {
        loginRepository.getLoginData()
        ConnectionObject.email = loginRepository.getLoginData().email
    }

    fun getKakaoLoginData() = viewModelScope.launch {

    }

    class LoginViewModelFactory(private val loginRepository: LoginRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(loginRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}