package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityChallengeBinding
import com.shootit.greme.model.*
import com.shootit.greme.ui.adapter.ChallengeActivityAdapter
import com.shootit.greme.viewmodel.ChallengeViewModel

class ChallengeActivity : BaseActivity<ActivityChallengeBinding>(R.layout.activity_challenge) {
    override val viewModel by viewModels<ChallengeViewModel> {
        ChallengeViewModel.ChallengeViewModelFactory("tmp")
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@ChallengeActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        val tmp = mutableListOf<ChallengeRecyclerType>(
            TopBar(2),
            Guide("참여중인 챌린지"),
            OnGoingChallenge("#가까운_거리는_걸어다니기", "도보로 15분 이내의 거리는 걸어다니기", 3, 23),
            OnGoingChallenge("#텀블러_이용하기", "카페가서 텀블러 이용하기", 12, 25),
            Guide("참여 가능 챌린지"),
            AvailableChallenge("#따뜻하게_입고_다니기", "난방 대신 따뜻한 옷을 입어요", 10, 13),

            )

        val adapter: ChallengeActivityAdapter = ChallengeActivityAdapter(resources)
        adapter.dataList = tmp
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

}