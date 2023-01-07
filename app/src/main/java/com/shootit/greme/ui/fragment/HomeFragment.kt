package com.shootit.greme.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentHomeBinding
import com.shootit.greme.viewmodel.ChallengeHomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel by viewModels<ChallengeHomeViewModel> {
        ChallengeHomeViewModel.ChallengeHomeViewModelFactory("tmp")
    }

    override fun initView() {

    }

}