package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class ResponseDateDiaryData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("hashtag")
    val hashtag: String,
    @SerializedName("image")
    val image: String
)
