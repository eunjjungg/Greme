package com.shootit.greme.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.*
import com.shootit.greme.repository.ChallengeRepository
import com.shootit.greme.util.RemainingDateCalculator.serverTimeToDDay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChallengeViewModel(private val challengeRepository: ChallengeRepository): ViewModel() {

    val challengeData = MutableLiveData<MutableList<ChallengeRecyclerType>>()

    fun getMyChallengeList(completion: (ChallengeActivityModel?) -> Unit) = viewModelScope.launch {
        val data: ChallengeActivityModel? = challengeRepository.getMyChallengeList()
        completion(data)
        if (data != null) {
            convertServerDataToRecyclerTypeData(data)
        }
    }

    private fun convertServerDataToRecyclerTypeData(rawData: ChallengeActivityModel) {
        val convertedData = mutableListOf<ChallengeRecyclerType>(
            TopBar(rawData.count),
            Guide("참여중인 챌린지"),
            Guide("참여 가능 챌린지"),
        )
        val onGoingChallengeList: MutableList<OnGoingChallenge> = mutableListOf()
        rawData.challengeJoinSummary?.forEach {
            onGoingChallengeList.add(it.convertDataToOnGoing())
        }
        val availableChallengeList: MutableList<AvailableChallenge> = mutableListOf()
        rawData.challengeSummary?.forEach {
            availableChallengeList.add(it.convertDataToAvailable())
        }
        convertedData.addAll(index = 2, elements = onGoingChallengeList)
        convertedData.addAll(availableChallengeList)
        challengeData.value = convertedData
    }

    private fun ChallengeDetail.convertDataToOnGoing(): OnGoingChallenge {
        return OnGoingChallenge(
            title = this.title,
            desc = this.info,
            day = this.deadline.serverTimeToDDay(),
            peopleAmount = this.num,
            id = this.id
        )
    }

    private fun ChallengeDetail.convertDataToAvailable(): AvailableChallenge {
        return AvailableChallenge(
            title = this.title,
            desc = this.info,
            day = this.deadline.serverTimeToDDay(),
            peopleAmount = this.num,
            id = this.id
        )
    }

    class ChallengeViewModelFactory(private val challengeRepository: ChallengeRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChallengeViewModel::class.java)) {
                return ChallengeViewModel(challengeRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}