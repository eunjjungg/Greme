package com.shootit.greme.ui.view

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityOtherUserProfileBinding
import com.shootit.greme.repository.OtherUserProfileRepository
import com.shootit.greme.ui.adapter.OtherUserProfileAdapter
import com.shootit.greme.viewmodel.OtherUserProfileViewModel

class OtherUserProfileActivity : BaseActivity<ActivityOtherUserProfileBinding>(R.layout.activity_other_user_profile) {
    override val viewModel by viewModels<OtherUserProfileViewModel> {
        OtherUserProfileViewModel.OtherUserProfileViewModelFactory(
            OtherUserProfileRepository.getInstance()!!
        )
    }

    private val adapter by lazy {
        OtherUserProfileAdapter(resources = resources)
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@OtherUserProfileActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {

    }


}