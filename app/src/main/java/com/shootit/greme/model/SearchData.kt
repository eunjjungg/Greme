package com.shootit.greme.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(
    val img: String,
    val postId: Int
) : Parcelable
