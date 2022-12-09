package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.LoginData
import com.shootit.greme.repository.LoginRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val loginRepository: LoginRepository): ViewModel() {




    class MainViewModelFactory(private val loginRepository: LoginRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(loginRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}