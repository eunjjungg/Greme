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
import com.shootit.greme.databinding.FragmentAdditionalInfoBinding
import com.shootit.greme.viewmodel.SignUpViewModel

class AdditionalInfoFragment : BaseFragment<FragmentAdditionalInfoBinding>(R.layout.fragment_additional_info) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.vm = viewModel
        binding.apply {  }
    }

}