package com.shootit.greme.ui.view

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityChallengeBinding
import com.shootit.greme.model.ChallengeActivityModel
import com.shootit.greme.repository.ChallengeRepository
import com.shootit.greme.ui.adapter.ChallengeActivityAdapter
import com.shootit.greme.viewmodel.ChallengeViewModel

class ChallengeActivity : BaseActivity<ActivityChallengeBinding>(R.layout.activity_challenge) {
    override val viewModel by viewModels<ChallengeViewModel> {
        ChallengeViewModel.ChallengeViewModelFactory(
            ChallengeRepository.getInstance()!!
        )
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@ChallengeActivity
        binding.vm = this.viewModel
    }

    private val adapter by lazy {
        ChallengeActivityAdapter(resources)
    }

    override fun onCreateAction() {
        ViewCompat.setTransitionName(binding.icon, "icon")
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.visibility = View.GONE
        viewModel.getMyChallengeList {
            val serverData: ChallengeActivityModel

            if (it == null) {
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                makeSnackBar("접속이 불안정합니다. 잠시 후 다시 시도해주세요.")
                return@getMyChallengeList
            } else {
                serverData = it
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }

            // TODO 데이터 세팅
            Log.d("ccheck challenge", it.toString())
        }
    }

    private fun setObserver() {
        viewModel.challengeData.observe(this, Observer {
            val changeRange = adapter.dataList.size - 2
            adapter.dataList = it
            adapter.notifyItemRangeChanged(2, changeRange)
        })
    }

    private fun makeSnackBar(message: String) {
        val snackbar = Snackbar.make(binding.recyclerView.parent as View, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("확인", View.OnClickListener {
            snackbar.dismiss()
        })
        snackbar.show()
    }
}