package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.GENDER
import com.shootit.greme.model.UserAreaAndPurposeInfo
import com.shootit.greme.model.UserInterestAndGenderInfo
import com.shootit.greme.repository.SettingRepository
import com.shootit.greme.util.EnumUtil.getGenderByInt
import com.shootit.greme.util.EnumUtil.getInterestByInt
import kotlinx.coroutines.launch

class SettingUserInfoViewModel(private val settingRepository: SettingRepository): ViewModel() {

    var gender: GENDER? = GENDER.Whatever
    var region: String = "선택 안 함"
    var purpose: String = "선택 안 함"
    val interestList = mutableListOf<SignUpViewModel.INTEREST>()

    fun getInitData(
        initInterest: (List<SignUpViewModel.INTEREST>) -> Unit,
        initGender: (GENDER) -> Unit,
        initArea: (String) -> Unit,
        initPurpose: (String) -> Unit,
        errorCase: () -> Unit
    ) = viewModelScope.launch {
        val data = settingRepository.getCurrentProfile()
        data?.let {
            it.interestType.forEach { interestInt ->
                interestList.add(interestInt.getInterestByInt())
            }
            gender = it.genderType.getGenderByInt()
            region = it.area
            purpose = it.purpose

            initInterest(interestList)
            initGender(it.genderType.getGenderByInt())
            initArea(it.area)
            initPurpose(it.purpose)
        }
        if (data == null) {
            errorCase()
        }
    }

    fun saveUserInfo(notAnswered: (String) -> Unit, completion: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (gender == null || interestList.size == 0) {
                notAnswered("모든 항목을 선택해주세요")
                return@launch
            }

            val response1 = settingRepository.setUserAreaAndPurpose(
                UserAreaAndPurposeInfo(area = region, purpose = purpose)
            )
            val interestIndexList = mutableListOf<Int>()
            interestList.forEach { interest ->
                interestIndexList.add(interest.index)
            }
            val response2 = settingRepository.setUserInterestAndGender(
                UserInterestAndGenderInfo(interestIndexList, gender!!.index)
            )

            completion(response1 && response2)
        }
    }

    fun interestSelected(title: String, isClicked: Boolean) {
        val settingList : (SignUpViewModel.INTEREST) -> Unit = { interest ->
            if(isClicked) {
                interestList.add(interest)
            } else {
                interestList.remove(interest)
            }
        }
        when (title) {
            SignUpViewModel.INTEREST.ENERGY.text -> settingList(SignUpViewModel.INTEREST.ENERGY)
            SignUpViewModel.INTEREST.UP_CYCLING.text -> settingList(SignUpViewModel.INTEREST.UP_CYCLING)
            SignUpViewModel.INTEREST.ECO_PRODUCT.text -> settingList(SignUpViewModel.INTEREST.ECO_PRODUCT)
            SignUpViewModel.INTEREST.VEGAN.text -> settingList(SignUpViewModel.INTEREST.VEGAN)
            SignUpViewModel.INTEREST.COSMETIC.text -> settingList(SignUpViewModel.INTEREST.COSMETIC)
        }
    }

    class SettingUserInfoViewModelFactory(private val settingRepository: SettingRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingUserInfoViewModel::class.java)) {
                return SettingUserInfoViewModel(settingRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}