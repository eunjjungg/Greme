package com.shootit.greme.model

import okhttp3.MultipartBody

data class DiaryWriteData(
    val creationReq: WriteData?,
    val multipartFile: MultipartBody.Part
)
data class WriteData(
    val content: String,
    val hashtag: String,
    val challenges: Long,
    val status: Boolean
)
