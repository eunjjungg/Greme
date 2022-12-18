package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivitySignUpBinding
import com.shootit.greme.ui.fragment.signup.SetIdFragment
import com.shootit.greme.ui.fragment.signup.SetInterestFragment
import com.shootit.greme.viewmodel.SignUpViewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    override val viewModel by viewModels<SignUpViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignUpViewModel("name") as T
            }
        }
    }
    

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@SignUpActivity
        binding.viewModel = this.viewModel
    }

    override fun onCreateAction() {
        supportFragmentManager.commit {
            replace(R.id.fragmentSignUp, SetInterestFragment())
        }
    }

}