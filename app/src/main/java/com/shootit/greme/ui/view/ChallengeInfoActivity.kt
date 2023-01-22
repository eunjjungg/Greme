package com.shootit.greme.ui.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityChallengeInfoBinding
import com.shootit.greme.model.ChallengeInfo
import com.shootit.greme.model.ChallengeInfoImg
import com.shootit.greme.model.ChallengeInfoParcelData
import com.shootit.greme.repository.ChallengeRepository
import com.shootit.greme.ui.adapter.ChallengeInfoRecyclerAdapter
import com.shootit.greme.viewmodel.ChallengeInfoViewModel

/**
 * 넘겨줘야되는 데이터
 * format : Parcel
 * data : ChallengeInfoParcelData(title, desc, dat, peopleAmount, isRegistered, id)
 */

class ChallengeInfoActivity :
    BaseActivity<ActivityChallengeInfoBinding>(R.layout.activity_challenge_info) {

    override val viewModel by viewModels<ChallengeInfoViewModel> {
        ChallengeInfoViewModel.ChallengeInfoViewModelFactory(
            ChallengeRepository.getInstance()!!
        )
    }

    private var id: Int = 0
    private val adapter by lazy {
        ChallengeInfoRecyclerAdapter(resources)
    }
    private var isParticipate: Boolean = false

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@ChallengeInfoActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        initRecyclerView()
        initFabByStatus(isParticipate)
        setListenerToFab()
        setObserver()
    }

    private fun initRecyclerView() {
        val transportedData: ChallengeInfoParcelData? = intent.getParcelableExtra<ChallengeInfoParcelData?>("ChallengeInfo")
        transportedData?.let {
            adapter.challengeInfo = ChallengeInfo(it.title, it.desc, it.day, it.isRegistered)
            isParticipate = it.isRegistered
            id = it.id
        }

        viewModel.getMyImageList(id)
        // adapter.imgDataList = imgData
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private  fun setObserver(){
        viewModel.challengeInfoImg.observe(this, Observer {
            adapter.imgDataList = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun initFabByStatus(isParticipate: Boolean) {
        when (isParticipate) {
            true -> {
                binding.fabStatus.apply {
                    backgroundTintList = ColorStateList.valueOf(
                        resources.getColor(R.color.challenge_info_exception)
                    )
                    text = resources.getString(R.string.challenge_info_exception)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_minus, null)
                }
            }
            false -> {
                binding.fabStatus.apply {
                    backgroundTintList = ColorStateList.valueOf(
                        resources.getColor(R.color.challenge_info_participate)
                    )
                    text = resources.getString(R.string.challenge_info_participate)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_challenge_plus, null)
                }
            }
        }
    }

    private fun setListenerToFab() {
        binding.fabStatus.setOnClickListener {
            if (isParticipate) {
                viewModel.exceptChallenge(id) {
                    isParticipate = !isParticipate
                    changeFabStatus(isParticipate)
                }
            } else {
                viewModel.participateChallenge(id) {
                    isParticipate = !isParticipate
                    changeFabStatus(isParticipate)
                }
            }
        }
    }

    private fun changeFabStatus(isParticipate: Boolean) {
        val colorException = resources.getColor(R.color.challenge_info_exception)
        val colorParticipate = resources.getColor(R.color.challenge_info_participate)

        when (isParticipate) {
            true -> {
                binding.fabStatus.apply {
                    colorTransitionAnim(from = colorParticipate, to = colorException)
                    text = resources.getString(R.string.challenge_info_exception)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_minus, null)
                }
            }
            false -> {
                binding.fabStatus.apply {
                    colorTransitionAnim(from = colorException, to = colorParticipate)
                    text = resources.getString(R.string.challenge_info_participate)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_challenge_plus, null)
                }
            }
        }
    }

    private fun colorTransitionAnim(from: Int, to: Int) {
        val anim = ValueAnimator.ofArgb(from, to).apply {
            duration = 300
            addUpdateListener {
                binding.fabStatus.setBackgroundColor(it.animatedValue as Int)
            }
        }.start()
    }

}