package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WelcomeViewModel(private val tmp: String): ViewModel() {

    class WelcomeViewModelFactory(private val tmp: String)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
                return WelcomeViewModel(tmp) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}