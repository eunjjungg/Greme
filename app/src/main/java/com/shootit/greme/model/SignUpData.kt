package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class SignUpData(
    val id: String,
    val recentInterest: RecentInterest
)

data class SignUpAccountData(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String
)

enum class RecentInterest(meaning: String) {
    Energy("에너지"),
    UpCycling("업사이클링"),
    EcoProduct("친환경 제품"),
    VeganFood("(비건) 채식"),
    VeganCosmetic("(비건) 화장품")
}

enum class GENDER(val text: String, val index: Int) {
    Male("남성", 0),
    Female("여성", 1),
    Whatever("무엇이든", 2)
}