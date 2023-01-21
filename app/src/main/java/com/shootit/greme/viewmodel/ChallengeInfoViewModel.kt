package com.shootit.greme.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shootit.greme.model.ChallengeInfoImg
import com.shootit.greme.model.ChallengeInfoModel
import com.shootit.greme.model.ChallengeParticipation
import com.shootit.greme.model.UrlAndId
import com.shootit.greme.repository.ChallengeRepository
import kotlinx.coroutines.launch

class ChallengeInfoViewModel(private val challengeRepository: ChallengeRepository): ViewModel() {

    var challengeInfoImg = MutableLiveData<MutableList<ChallengeInfoImg>>()

    fun getMyImageList(id: Int) = viewModelScope.launch {
        val data: ChallengeInfoModel? = challengeRepository.getChallengeInfo(id)
        if (data != null) {
            setDataToChallengeImgLiveData(data.getChallengeLists)
        }
    }

    private fun setDataToChallengeImgLiveData(data: List<ChallengeParticipation>) {
        val imgList = mutableListOf<ChallengeInfoImg>()
        val size = data.size
        val q = size / 3
        val r = size % 3


        for (i in 0 until q) {
            val tmp = mutableListOf<UrlAndId>()
            for (j in 0..2) {
                tmp.add(UrlAndId(data[i * 3 + j].image, data[i * 3 + j].id))
            }
            imgList.add(ChallengeInfoImg(tmp[0], tmp[1], tmp[2]))
        }

        if (r > 0) {
            val tmp = mutableListOf<UrlAndId>()
            for (i in 1 .. r) {
                tmp.add(UrlAndId(data[size - i].image, data[size - i].id))
            }
        }

        challengeInfoImg.value = imgList
    }

    fun participateChallenge(id: Int, completion: (Boolean) -> Unit) = viewModelScope.launch {
        val response = challengeRepository.participateChallenge(id)
        completion(response)
    }

    fun exceptChallenge(id: Int, completion: (Boolean) -> Unit) = viewModelScope.launch {
        val response = challengeRepository.exceptChallenge(id)
        completion(response)
    }

    class ChallengeInfoViewModelFactory(private val challengeRepository: ChallengeRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChallengeInfoViewModel::class.java)) {
                return ChallengeInfoViewModel(challengeRepository) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}