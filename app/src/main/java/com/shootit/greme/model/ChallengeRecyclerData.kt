package com.shootit.greme.model

// top bar, ongoing, available, guide
data class ChallengeRecyclerData(
    val type: ChallengeRecyclerType
)

sealed class ChallengeRecyclerType()

class TopBar(
    val successCount: Int
) : ChallengeRecyclerType()

class OnGoingChallenge(
    val title: String,
    val desc: String,
    val day: Int,
    val peopleAmount: Int,
    val id: Int
) : ChallengeRecyclerType()

class AvailableChallenge(
    val title: String,
    val desc: String,
    val day: Int,
    val peopleAmount: Int,
    val id: Int
) : ChallengeRecyclerType()

class Guide(
    val text: String
) : ChallengeRecyclerType()
