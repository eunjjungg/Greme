package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class ResponseSearchData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)
