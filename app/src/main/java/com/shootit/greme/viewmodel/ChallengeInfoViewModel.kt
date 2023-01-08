package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChallengeInfoViewModel(private val tmp: String): ViewModel() {

    class ChallengeInfoViewModelFactory(private val tmp: String)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChallengeInfoViewModel::class.java)) {
                return ChallengeInfoViewModel(tmp) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}