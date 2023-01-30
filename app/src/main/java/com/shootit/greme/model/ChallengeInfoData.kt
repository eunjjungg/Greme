package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

sealed class ChallengeInfoType()

class ChallengeInfo(
    val title: String,
    val desc: String,
    val day: Int,
    var isRegistered: Boolean
) : ChallengeInfoType()

class ChallengeInfoImg(
    var firstImgUrl : UrlAndId,
    var secondImgUrl : UrlAndId?,
    var thirdImgUrl : UrlAndId?
) : ChallengeInfoType()

data class UrlAndId(
    @SerializedName("image")
    val urlString: String,
    @SerializedName("id")
    val id: Int
)