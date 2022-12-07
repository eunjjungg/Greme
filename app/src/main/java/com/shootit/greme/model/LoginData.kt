package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("email")
    val email: String
)