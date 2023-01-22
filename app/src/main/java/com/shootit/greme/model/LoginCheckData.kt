package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class LoginCheckData(
    @SerializedName("isExistingUser")
    val isExistingUser: Boolean,
    @SerializedName("email")
    val email: String,
    @SerializedName("accessToken")
    val accessToken: String
)