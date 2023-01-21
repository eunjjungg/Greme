package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingUserInfoViewModel(private val tmp: String): ViewModel() {

    class SettingUserInfoViewModelFactory(private val tmp: String)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingUserInfoViewModel::class.java)) {
                return SettingUserInfoViewModel(tmp) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}