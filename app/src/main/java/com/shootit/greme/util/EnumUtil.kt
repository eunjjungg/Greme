package com.shootit.greme.util

import com.shootit.greme.model.GENDER
import com.shootit.greme.viewmodel.SignUpViewModel

object EnumUtil {
    fun Int.getGenderByInt(): GENDER {
        return when (this) {
            0 -> GENDER.Male
            1 -> GENDER.Female
            else -> GENDER.Whatever
        }
    }

    fun Int.getInterestByInt(): SignUpViewModel.INTEREST {
        return when (this) {
            0 -> SignUpViewModel.INTEREST.ENERGY
            1 -> SignUpViewModel.INTEREST.UP_CYCLING
            2 -> SignUpViewModel.INTEREST.ECO_PRODUCT
            3 -> SignUpViewModel.INTEREST.VEGAN
            else -> SignUpViewModel.INTEREST.COSMETIC
        }
    }
}