package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class ChallengeActivityModel(
    // 참여 중인 챌린지 있는지
    @SerializedName("exist")
    val exist: Boolean,
    // 챌린지 참여 기록 있는지
    @SerializedName("record")
    val record: Boolean,
    // 참여 가능 챌린지 목록
    @SerializedName("challengeSummary")
    val challengeSummary: List<ChallengeDetail>?,
    // 참여 중인 챌린지 목록
    @SerializedName("challengeJoinSummary")
    val challengeJoinSummary: List<ChallengeDetail>?,
    // 이번 주 참여 횟수
    @SerializedName("count")
    val count: Int
)

data class ChallengeInfoModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("getChallengeLists")
    val getChallengeLists: List<ChallengeParticipation>,
    @SerializedName("summaryRes")
    val summaryRes: ChallengeDetail
)

data class ChallengeParticipation(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)

data class ChallengeDetail(
    @SerializedName("num")
    val num: Int,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("info")
    val info: String,
    @SerializedName("id")
    val id: Int
)