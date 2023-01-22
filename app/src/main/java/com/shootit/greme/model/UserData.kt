package com.shootit.greme.model

import androidx.browser.customtabs.CustomTabsService.FilePurpose
import com.google.gson.annotations.SerializedName

data class UserInterestData(
    @SerializedName("interestType")
    var interestList: List<Int>
)

data class UserAdditionalInfo(
    @SerializedName("genderType")
    var genderType: Int,
    @SerializedName("area")
    var area: String = "선택 안 함",
    @SerializedName("purpose")
    var purpose: String = "선택 안 함"
)
