package com.shootit.greme.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChallengeGuideViewModel : ViewModel() {

    data class CardData(
        val title: String,
        val detail: String,
        var isOpen: MutableState<Boolean> = mutableStateOf(false)
    )

    private val card1 = CardData(
        "그리미란?",
        "그리미는 한 줄로 기록하는 나의 에코 챌린지 다이어리 어플입니다."
    )

    private val card2 = CardData(
        "챌린지란?",
        "가볍게는 일주일, 길면 한 달동안 진행되는 생활 속 환경 지킴 운동입니다."
    )

    private val card3 = CardData(
        "챌린지 참여 방법은?",
        "메인 화면에서 챌린지를 클릭해 원하시는 챌린지를 등록하시고, 다이어리 탭에 들어가시면 해당 챌린지의 해시태그가 뜹니다. 해당 해시태그를 클릭하신 후, 한 줄 또는 한 장의 사진과 함께 챌린지 관련 게시물을 작성하시면 챌린지에 참여하실 수 있습니다."
    )

    var cardDataList by mutableStateOf(
        mutableListOf<CardData>(card1, card2, card3)
    )

    class ChallengeGuideViewModelFactory() :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChallengeGuideViewModel() as T
        }
    }
}