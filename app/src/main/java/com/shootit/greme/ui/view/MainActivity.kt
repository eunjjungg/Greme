package com.shootit.greme.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityMainBinding
import com.shootit.greme.repository.LoginRepository
import com.shootit.greme.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel by lazy {
        ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(LoginRepository.getInstance(application)!!)
        ).get(MainViewModel::class.java)
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@MainActivity
        binding.viewModel = this.viewModel
    }

    override fun onCreateAction() {
        binding.btnLogin.setOnClickListener {
            Intent(this@MainActivity, LoginActivity::class.java)
                .also { startActivity(it) }
        }
    }

}