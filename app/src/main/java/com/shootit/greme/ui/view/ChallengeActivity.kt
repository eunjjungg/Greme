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
            Guide("Guide"),
            OnGoingChallenge("ongoing", "ongoing desc", 3, 3),
            Guide("Guide"),
            AvailableChallenge("avail", "avail desc", 10, 10),

            )

        val adapter: ChallengeActivityAdapter = ChallengeActivityAdapter(resources)
        adapter.dataList = tmp
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

}