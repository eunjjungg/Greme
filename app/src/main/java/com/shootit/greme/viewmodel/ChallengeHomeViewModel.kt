package com.shootit.greme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.ChallengeInfoParcelData
import com.shootit.greme.model.FeedDetail
import com.shootit.greme.repository.ChallengeRepository
import com.shootit.greme.util.RemainingDateCalculator.serverTimeToDDay
import kotlinx.coroutines.launch

class ChallengeHomeViewModel(private val challengeRepository: ChallengeRepository): ViewModel() {

    fun getHomeFeedData(
        popularCompletion: (FeedDetail?) -> Unit, participationCompletion: (FeedDetail?) -> Unit
    ) = viewModelScope.launch {
        challengeRepository.getChallengeHomeFeedList()?.let {
            popularCompletion(it.popularityChallenge)
            participationCompletion(it.participatingChallenge)
        }
    }

    fun getParcelData(id: Int, completion: (ChallengeInfoParcelData) -> Unit) = viewModelScope.launch {
        val info = challengeRepository.getChallengeInfo(id)
        info?.let {
            val parcel = ChallengeInfoParcelData(
                it.summaryRes.title, it.summaryRes.info, it.summaryRes.deadline.serverTimeToDDay(),
                it.summaryRes.num, it.status, it.summaryRes.id
            )
            completion(parcel)
        }
    }



    class ChallengeHomeViewModelFactory(private val challengeRepository: ChallengeRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChallengeHomeViewModel::class.java)) {
                return ChallengeHomeViewModel(challengeRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}