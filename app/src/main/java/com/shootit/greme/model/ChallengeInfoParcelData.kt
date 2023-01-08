package com.shootit.greme.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ChallengeInfoParcelData(
    val title: String,
    val desc: String,
    val day: Int,
    val peopleAmount: Int
): Parcelable