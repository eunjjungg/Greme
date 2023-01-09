package com.shootit.greme.model

sealed class ChallengeInfoType()

class ChallengeInfo(
    val title: String,
    val desc: String,
    val day: Int,
    var isRegistered: Boolean
) : ChallengeInfoType()

class ChallengeInfoGuide() : ChallengeInfoType()

class ChallengeInfoImg(urlString: String) : ChallengeInfoType()