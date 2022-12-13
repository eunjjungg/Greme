package com.shootit.greme.ui.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetAdditionalInfoBinding
import com.shootit.greme.viewmodel.SignUpViewModel

class SetAdditionalInfoFragment
    : BaseFragment<FragmentSetAdditionalInfoBinding>(
    R.layout.fragment_set_additional_info
) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {

    }

}