package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class ChallengeActivityModel(
    @SerializedName("exist")
    val exist: Boolean,
    @SerializedName("record")
    val record: Boolean,
    @SerializedName("challengeSummary")
    val challengeSummary: List<ChallengeDetail>?,
    @SerializedName("challengeJoinSummary")
    val challengeJoinSummary: List<ChallengeDetail>?,
    @SerializedName("count")
    val count: Int
)

data class ChallengeDetail(
    @SerializedName("num")
    val num: Int,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("info")
    val info: String
)