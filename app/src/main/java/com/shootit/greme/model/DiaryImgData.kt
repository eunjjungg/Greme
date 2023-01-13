package com.shootit.greme.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class DiaryImgData (
    val date : String,
    val img : ArrayList<Int>)