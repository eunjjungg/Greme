package com.shootit.greme.model

data class SignUpData(
    val id: String,
    val recentInterest: RecentInterest
)

enum class RecentInterest(meaning: String) {
    Energy("에너지"),
    UpCycling("업사이클링"),
    EcoProduct("친환경 제품"),
    VeganFood("(비건) 채식"),
    VeganCosmetic("(비건) 화장품")
}

