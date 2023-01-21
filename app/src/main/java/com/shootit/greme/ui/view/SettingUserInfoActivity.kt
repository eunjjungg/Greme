package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivitySettingUserInfoBinding
import com.shootit.greme.repository.ChallengeRepository
import com.shootit.greme.viewmodel.ChallengeInfoViewModel
import com.shootit.greme.viewmodel.SettingUserInfoViewModel

class SettingUserInfoActivity : BaseActivity<ActivitySettingUserInfoBinding>(R.layout.activity_setting_user_info) {
    override val viewModel by viewModels<SettingUserInfoViewModel> {
        SettingUserInfoViewModel.SettingUserInfoViewModelFactory(
            "tmp"
        )
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@SettingUserInfoActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {

    }

}