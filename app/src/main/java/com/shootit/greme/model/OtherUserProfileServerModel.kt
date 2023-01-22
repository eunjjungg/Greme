package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class OtherUserProfileModel(
    @SerializedName("username")
    val username: String,
    @SerializedName("profileImg")
    val profileImg: String,
    @SerializedName("challengeSummary")
    val challengeSummary: List<ChallengeDetail?>,
    @SerializedName("postRes")
    val urlAndId: List<UrlAndId?>
)

