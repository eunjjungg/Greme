package com.shootit.greme.ui.view

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityOtherUserProfileBinding
import com.shootit.greme.repository.OtherUserProfileRepository
import com.shootit.greme.ui.adapter.OtherUserProfileAdapter
import com.shootit.greme.viewmodel.OtherUserProfileViewModel

/**
 * 넘겨받아야 하는 데이터 : userId !!
 * format : Intent.putInt("userId")
 */

class OtherUserProfileActivity : BaseActivity<ActivityOtherUserProfileBinding>(R.layout.activity_other_user_profile) {
    override val viewModel by viewModels<OtherUserProfileViewModel> {
        OtherUserProfileViewModel.OtherUserProfileViewModelFactory(
            OtherUserProfileRepository.getInstance()!!
        )
    }

    private val adapter by lazy {
        OtherUserProfileAdapter(resources = resources)
    }

    private val userId by lazy {
        intent.getIntExtra("userId", 0)
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@OtherUserProfileActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        getDataFromServer()
    }

    private fun getDataFromServer() {
        viewModel.getUserInfoFromServer(
            userId = userId,
            compUserName = { adapter.userName = it },
            compUserImg = { adapter.userImageString = it },
            compUserChallenge = { adapter.challengeList = it },
            errorCase = { makeSnackBar("접속이 불안정합니다. 잠시 후 다시 시도해주세요.") }
        )
    }

    private fun makeSnackBar(message: String) {
        val snackbar = Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("확인", View.OnClickListener {
            snackbar.dismiss()
        })
        snackbar.show()
    }
}