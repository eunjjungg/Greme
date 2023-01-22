package com.shootit.greme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.ChallengeDetail
import com.shootit.greme.model.OnGoingChallenge
import com.shootit.greme.model.OtherUserProfileModel
import com.shootit.greme.repository.OtherUserProfileRepository
import com.shootit.greme.util.RemainingDateCalculator.serverTimeToDDay
import kotlinx.coroutines.launch

class OtherUserProfileViewModel(
    private val otherUserProfileRepository: OtherUserProfileRepository
    ) : ViewModel() {

    fun getUserInfoFromServer(
        userId: Int,
        compUserName: (String) -> Unit,
        compUserImg: (String) -> Unit,
        compUserChallenge: (MutableList<OnGoingChallenge>) -> Unit,
        errorCase: (String) -> Unit
    ) = viewModelScope.launch {
        val data = otherUserProfileRepository.getOtherUserProfile(userId)

        data?.let {
            Log.d("ccheck data", it.toString())
            compUserName(it.profileImg)
            compUserImg(it.profileImg)
            if (it.challengeSummary.isNotEmpty()) {
                compUserChallenge(it.challengeSummary.convertServerModelToOnGoingChallengeModel())
            }
            return@launch
        }

        errorCase("접속이 불안정합니다. 잠시 후 다시 시도해주세요.")
        return@launch
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