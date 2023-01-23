package com.shootit.greme.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.*
import com.shootit.greme.repository.OtherUserProfileRepository
import com.shootit.greme.util.RemainingDateCalculator.serverTimeToDDay
import kotlinx.coroutines.launch

class OtherUserProfileViewModel(
    private val otherUserProfileRepository: OtherUserProfileRepository
    ) : ViewModel() {

    var challengeInfoImg = MutableLiveData<MutableList<ChallengeInfoImg>>()
    val userName = MutableLiveData<String>()

    fun getUserInfoFromServer(
        userId: Int,
        compUserImg: (String) -> Unit,
        compUserChallenge: (MutableList<OnGoingChallenge>) -> Unit,
        errorCase: (String) -> Unit
    ) = viewModelScope.launch {
        val data = otherUserProfileRepository.getOtherUserProfile(userId)

        data?.let {
            if (it.username == null) {
                errorCase("올바르지 않은 유저입니다.")
                return@launch
            }
            userName.value = it.username!!

            it.profileImg?.let { img ->
                compUserImg(img)
            }
            if (it.challengeSummary.isNotEmpty()) {
                compUserChallenge(it.challengeSummary.convertServerModelToOnGoingChallengeModel())
            }
            setDataToChallengeImgLiveData(it.urlAndIdList)

            return@launch
        }


        errorCase("접속이 불안정합니다. 잠시 후 다시 시도해주세요.")
        return@launch
    }

    private fun setDataToChallengeImgLiveData(data: List<UrlAndId>) {
        val imgList = mutableListOf<ChallengeInfoImg>()
        val size = data.size
        val q = size / 3
        val r = size % 3


        for (i in 0 until q) {
            val tmp = mutableListOf<UrlAndId>()
            for (j in 0..2) {
                tmp.add(UrlAndId(data[i * 3 + j].urlString, data[i * 3 + j].id))
            }
            imgList.add(ChallengeInfoImg(tmp[0], tmp[1], tmp[2]))
        }

        if (r > 0) {
            val tmp = mutableListOf<UrlAndId>()
            for (i in 1 .. r) {
                tmp.add(UrlAndId(data[size - i].urlString, data[size - i].id))
            }
            when(tmp.size) {
                1 -> imgList.add(ChallengeInfoImg(tmp[0], null, null))
                2 -> imgList.add(ChallengeInfoImg(tmp[0], tmp[1], null))
                3 -> imgList.add(ChallengeInfoImg(tmp[0], tmp[1], tmp[2]))
            }
        }

        challengeInfoImg.value = imgList
    }

    private fun List<ChallengeDetail?>.convertServerModelToOnGoingChallengeModel()
            : MutableList<OnGoingChallenge> {
        val result = mutableListOf<OnGoingChallenge>()
        this.forEach {
            it?.let {
                result.add(
                    OnGoingChallenge(
                        it.title, it.info, it.deadline.serverTimeToDDay(),
                        it.num, it.id
                    )
                )
            }
        }
        return result
    }

    class OtherUserProfileViewModelFactory(private val otherUserProfileRepository: OtherUserProfileRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OtherUserProfileViewModel::class.java)) {
                return OtherUserProfileViewModel(otherUserProfileRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}