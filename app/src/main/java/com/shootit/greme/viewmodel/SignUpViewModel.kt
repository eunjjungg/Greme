package com.shootit.greme.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface
import com.shootit.greme.ui.view.InterestButton

class SignUpViewModel(t: String): ViewModel() {
    val errorText = MutableLiveData<Int?>(null)
    val guideText = MutableLiveData<Int?>(null)
    var id: String = ""

    enum class INTEREST(val text: String, val index: Int) {
        ENERGY("에너지", 0),
        UP_CYCLING("업사이클링", 1),
        ECO_PRODUCT("친환경 제품", 2),
        VEGAN("(비건) 채식", 3),
        COSMETIC("(비건) 화장품", 4)
    }

    private val interestList = mutableListOf<Boolean>(false, false, false, false, false)

    fun interestSelected(title: String, isClicked: Boolean) {
        when(title) {
            INTEREST.ENERGY.text -> interestList[INTEREST.ENERGY.index] = isClicked
            INTEREST.UP_CYCLING.text -> interestList[INTEREST.UP_CYCLING.index] = isClicked
            INTEREST.ECO_PRODUCT.text -> interestList[INTEREST.ECO_PRODUCT.index] = isClicked
            INTEREST.VEGAN.text -> interestList[INTEREST.VEGAN.index] = isClicked
            INTEREST.COSMETIC.text -> interestList[INTEREST.COSMETIC.index] = isClicked
        }
        Log.d("ccheck", interestList.toString())
    }

    fun isIdProper(id: String) {
        this.id = id
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