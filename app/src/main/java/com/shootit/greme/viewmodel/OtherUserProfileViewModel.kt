package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shootit.greme.repository.OtherUserProfileRepository

class OtherUserProfileViewModel(private val otherUserProfileRepository: OtherUserProfileRepository): ViewModel() {



    class OtherUserProfileViewModelFactory(private val otherUserProfileRepository: OtherUserProfileRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OtherUserProfileViewModel::class.java)) {
                return OtherUserProfileViewModel(otherUserProfileRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}