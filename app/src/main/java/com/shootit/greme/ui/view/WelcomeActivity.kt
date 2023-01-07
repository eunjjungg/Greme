package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityWelcomeBinding
import com.shootit.greme.viewmodel.WelcomeViewModel

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {

    override val viewModel by viewModels<WelcomeViewModel> {
        WelcomeViewModel.WelcomeViewModelFactory("tmp")
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@WelcomeActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        // TODO("Not yet implemented")
    }


}