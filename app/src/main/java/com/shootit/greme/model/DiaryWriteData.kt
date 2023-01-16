package com.shootit.greme.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class DiaryWriteData(
    @SerializedName("creationReq")
    val creationReq: WriteData?,
    @SerializedName("multipartFile")
    val multipartFile: MultipartBody.Part
)
data class WriteData(
    @SerializedName("content")
    val content: String,
    @SerializedName("hashtag")
    val hashtag: String,
    @SerializedName("challenges")
    val challenges: Long,
    @SerializedName("status")
    val status: Boolean
)
