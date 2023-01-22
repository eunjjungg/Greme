package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class ResponseSettingInfoData(
    @SerializedName("username")
    val username: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("challengeJoinSummary")
    val challengeJoinSummary: List<SettingChallengeInfo>
)
data class SettingChallengeInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("num")
    val num: Int,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("info")
    val info: String
)
