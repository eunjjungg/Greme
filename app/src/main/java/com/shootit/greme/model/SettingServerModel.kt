package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class UserCurrentInfo(
    @SerializedName("username")
    val username: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("interestType")
    val interestType: List<Int>,
    @SerializedName("genderType")
    val genderType: Int,
    @SerializedName("area")
    val area: String,
    @SerializedName("purpose")
    val purpose: String,
)

data class UserInterestAndGenderInfo(
    @SerializedName("interestType")
    val interestType: List<Int>,
    @SerializedName("genderType")
    val genderType: Int
)

data class UserAreaAndPurposeInfo(
    @SerializedName("area")
    val area: String,
    @SerializedName("purpose")
    val purpose: String
)