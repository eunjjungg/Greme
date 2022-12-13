package com.shootit.greme.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shootit.greme.R

class SignUpViewModel(t: String): ViewModel() {
    val errorText = MutableLiveData<Int?>(null)
    val guideText = MutableLiveData<Int?>(null)


    fun isIdProper(id: String) {
        if( !id.isAlphaNumeric() ) {
            guideText.value = null
            errorText.value = R.string.signup_char
        } else if( !id.isLengthProper() ) {
            guideText.value = null
            errorText.value = R.string.signup_length
        } else if ( !id.isBlankNotIncluded() ) {
            guideText.value = null
            errorText.value = R.string.signup_need_no_blank
        } else {
            errorText.value = null
            guideText.value = R.string.signup_check_duplicate
        }
    }

    private fun String.isAlphaNumeric(): Boolean {
        this.forEach {
            if (
                it !in 'A'..'Z' && it !in 'a'..'z' && it !in '0'..'9' && it !in '_'..'_'
            ) {
                return false
            }
        }
        return true
    }

    private fun String.isLengthProper(): Boolean {
        if( this.length !in 3..15 ) {
            return false
        }
        return true
    }

    private fun String.isBlankNotIncluded(): Boolean {
        this.forEach {
            if ( it != ' ' ) {
                return true
            }
        }
        return false
    }


}