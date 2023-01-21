package com.shootit.greme.model

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
    val urlString: String,
    val id: Int
)