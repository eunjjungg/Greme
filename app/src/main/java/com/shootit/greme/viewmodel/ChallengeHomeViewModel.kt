package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChallengeHomeViewModel(private val tmp: String): ViewModel() {





    class ChallengeHomeViewModelFactory(private val tmp: String)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChallengeHomeViewModel::class.java)) {
                return ChallengeHomeViewModel(tmp) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}