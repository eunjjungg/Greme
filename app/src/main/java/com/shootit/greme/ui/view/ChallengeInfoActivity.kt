package com.shootit.greme.ui.view

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityChallengeInfoBinding
import com.shootit.greme.model.ChallengeInfo
import com.shootit.greme.model.ChallengeInfoParcelData
import com.shootit.greme.ui.adapter.ChallengeInfoRecyclerAdapter
import com.shootit.greme.viewmodel.ChallengeInfoViewModel

class ChallengeInfoActivity :
    BaseActivity<ActivityChallengeInfoBinding>(R.layout.activity_challenge_info) {

    override val viewModel by viewModels<ChallengeInfoViewModel> {
        ChallengeInfoViewModel.ChallengeInfoViewModelFactory("tmp")
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@ChallengeInfoActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        val transportedData: ChallengeInfoParcelData? = intent.getParcelableExtra<ChallengeInfoParcelData?>("ChallengeInfo")
        Log.d("ccheck", transportedData.toString())
        val adapter = ChallengeInfoRecyclerAdapter(resources)
        transportedData?.let {
            adapter.challengeInfo = ChallengeInfo(transportedData.title, transportedData.desc, transportedData.day)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

}