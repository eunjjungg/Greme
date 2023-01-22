package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class ResponseEntireDiaryData(
    @SerializedName("date")
    val date: String,
    @SerializedName("postInfos")
    val postInfos: List<EntireDiary>
)
data class EntireDiary(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)
