package com.shootit.greme.viewmodel

import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.model.GENDER
import com.shootit.greme.model.SignUpAccountData
import com.shootit.greme.model.UserAdditionalInfo
import com.shootit.greme.model.UserInterestData
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.repository.SignUpRepository
import com.shootit.greme.util.EncryptedSpfImpl
import kotlinx.coroutines.launch
import kotlin.math.sign

class SignUpViewModel(private val signUpRepository: SignUpRepository) : ViewModel() {
    val errorText = MutableLiveData<Int?>(null)
    val guideText = MutableLiveData<Int?>(null)
    var id: String = ""
    var gender: GENDER? = null
    var region: String = ""
    var purpose: String = ""

    private var timer: CountDownTimer? = null
    private val CHECK_NICKNAME_TIME_INTERVAL = 1000L

    enum class INTEREST(val text: String, val index: Int) {
        ENERGY("에너지", 0),
        UP_CYCLING("업사이클링", 1),
        ECO_PRODUCT("친환경 제품", 2),
        VEGAN("(비건) 채식", 3),
        COSMETIC("(비건) 화장품", 4)
    }

    enum class SIGNUP_FRAGMENT(val index: Int) {
        INTEREST(0), MORE_INFO(1), ID(2), FINISH(3), TERM(4)
    }

    val interestList = mutableListOf<Boolean>(false, false, false, false, false)

    val fragmentTransition = MutableLiveData<SIGNUP_FRAGMENT>(SIGNUP_FRAGMENT.TERM)

    fun interestSelected(title: String, isClicked: Boolean) {
        when (title) {
            INTEREST.ENERGY.text -> interestList[INTEREST.ENERGY.index] = isClicked
            INTEREST.UP_CYCLING.text -> interestList[INTEREST.UP_CYCLING.index] = isClicked
            INTEREST.ECO_PRODUCT.text -> interestList[INTEREST.ECO_PRODUCT.index] = isClicked
            INTEREST.VEGAN.text -> interestList[INTEREST.VEGAN.index] = isClicked
            INTEREST.COSMETIC.text -> interestList[INTEREST.COSMETIC.index] = isClicked
        }
    }

    fun transitionToID() {
        fragmentTransition.value = SIGNUP_FRAGMENT.ID
    }

    fun transitionToMoreInfo() {
        fragmentTransition.value = SIGNUP_FRAGMENT.MORE_INFO
    }

    fun transitionToInterest() {
        fragmentTransition.value = SIGNUP_FRAGMENT.INTEREST
    }

    fun finishSignUp() {
        fragmentTransition.value = SIGNUP_FRAGMENT.FINISH
    }

    fun isIdProper(id: String) {
        this.id = id
        if (!id.isAlphaNumeric()) {
            guideText.value = null
            errorText.value = R.string.signup_char
        } else if (!id.isLengthProper()) {
            guideText.value = null
            errorText.value = R.string.signup_length
        } else if (!id.isBlankNotIncluded()) {
            guideText.value = null
            errorText.value = R.string.signup_need_no_blank
        } else {
            errorText.value = null
            guideText.value = R.string.signup_check_duplicate
        }

        if (guideText.value != null) {
            timer?.let {
                timer!!.cancel()
            }
            timer = object : CountDownTimer(CHECK_NICKNAME_TIME_INTERVAL, 500) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    checkUserNameDuplicated(id)
                }
            }.start()

        }
    }

    private fun checkUserNameDuplicated(id: String) {
        viewModelScope.launch {
            if (signUpRepository.checkUserNameDuplicated(id)) {
                guideText.value = R.string.signup_ok
                errorText.value = null
            } else {
                guideText.value = null
                errorText.value = R.string.signup_duplicate
            }
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
        if (this.length !in 3..15) {
            return false
        }
        return true
    }

    private fun String.isBlankNotIncluded(): Boolean {
        this.forEach {
            if (it != ' ') {
                return true
            }
        }
        return false
    }

    fun makeAccount(spf: SharedPreferences, completion: (Boolean) -> Unit) {
        viewModelScope.launch {
            var token = signUpRepository.makeAccount(SignUpAccountData(EncryptedSpfImpl(spf).getUserEmail()!!, id))
            Log.d("ccheck", token.toString())
            Base64.encodeToString(token?.toByteArray(), Base64.DEFAULT)
            var result = false
            token?.let {
                ConnectionObject.token = token
                EncryptedSpfImpl(spf).setAccessToken(it)
                result = true
            }
            completion(result)
        }
    }

    fun setInterest(completion: (Boolean) -> Unit) {
        viewModelScope.launch {
            var interestData = mutableListOf<Int>()
            interestList.forEachIndexed { index, boolean ->
                if(boolean) {
                    interestData.add(index)
                }
            }
            if(interestData.isNotEmpty()) {
                val result = signUpRepository.setInterest(UserInterestData(interestData))
                completion(result)
            }
        }
    }

    fun setAdditionalInfo(completion: (Boolean) -> Unit) {
        viewModelScope.launch {
            val genderData: GENDER = gender ?: GENDER.Whatever
            val result = signUpRepository.setAdditionalInfo(
                UserAdditionalInfo(
                    genderData.index,
                    region,
                    purpose
                )
            )
            completion(result)
        }
    }

    class SignUpViewModelFactory(private val signUpRepository: SignUpRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(signUpRepository) as T
        }
    }

}