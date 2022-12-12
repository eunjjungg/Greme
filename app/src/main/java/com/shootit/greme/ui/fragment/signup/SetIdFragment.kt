package com.shootit.greme.ui.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetIdBinding
import com.shootit.greme.viewmodel.SignUpViewModel

class SetIdFragment : BaseFragment<FragmentSetIdBinding>(R.layout.fragment_set_id) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.apply {

        }
    }

}