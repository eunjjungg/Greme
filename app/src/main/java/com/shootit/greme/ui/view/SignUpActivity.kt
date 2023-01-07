package com.shootit.greme.ui.view

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivitySignUpBinding
import com.shootit.greme.repository.SignUpRepository
import com.shootit.greme.ui.fragment.signup.AdditionalInfoFragment
import com.shootit.greme.ui.fragment.signup.SetIdFragment
import com.shootit.greme.ui.fragment.signup.SetInterestFragment
import com.shootit.greme.viewmodel.SignUpViewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    override val viewModel by viewModels<SignUpViewModel> {
        SignUpViewModel.SignUpViewModelFactory(
            SignUpRepository.getInstance()!!
        )
    }
    

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@SignUpActivity
        binding.viewModel = this.viewModel
    }

    override fun onCreateAction() {
        supportFragmentManager.commit {
            replace(R.id.fragmentSignUp, SetIdFragment())
        }
        setObserver()
    }

    private fun setObserver() {
        viewModel.fragmentTransition.observe(this, Observer {
            if(it.index == SignUpViewModel.SIGNUP_FRAGMENT.MORE_INFO.index) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentSignUp, AdditionalInfoFragment())
                    .addToBackStack(null)
                    .commit()
            }
            else if(it.index == SignUpViewModel.SIGNUP_FRAGMENT.ID.index){
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentSignUp, SetIdFragment())
                    .commit()
            }
            else if(it.index == SignUpViewModel.SIGNUP_FRAGMENT.INTEREST.index) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentSignUp, SetInterestFragment())
                    .commit()
            }
            else if (it.index == SignUpViewModel.SIGNUP_FRAGMENT.FINISH.index) {
                Intent(this@SignUpActivity, WelcomeActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        })
    }
}