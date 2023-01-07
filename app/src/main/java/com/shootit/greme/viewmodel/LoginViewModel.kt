package com.shootit.greme.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.repository.LoginRepository
import com.shootit.greme.ui.view.LoginFinishInterface
import com.shootit.greme.util.EncryptedSpfImpl
import com.shootit.greme.util.EncryptedSpfObject
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {
    fun getLoginData(domain: String, spf: SharedPreferences, callback: LoginFinishInterface) = viewModelScope.launch {
        Log.d("update login data", "login data to server")

        var isExistingUser: Boolean = false

        loginRepository.getLoginData(domain = domain).also {
            Log.d("ccheck", it.toString())
            ConnectionObject.email = it.email
            ConnectionObject.token = it.accessToken
            isExistingUser = it.isExistingUser
            EncryptedSpfImpl(spf).apply {
                setUserEmail(ConnectionObject.email)
                setAccessToken(ConnectionObject.token)
            }
        }

        if(EncryptedSpfImpl(spf).getUserEmail() != "") {
            callback.openActivityCallback(isExistingUser = isExistingUser)
        } else {
            callback.errorCallback()
        }
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