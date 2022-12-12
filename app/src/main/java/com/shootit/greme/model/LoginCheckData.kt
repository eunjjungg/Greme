package com.shootit.greme.model

import com.google.gson.annotations.SerializedName

data class LoginCheckData(
    @SerializedName("email")
    val email: String
)