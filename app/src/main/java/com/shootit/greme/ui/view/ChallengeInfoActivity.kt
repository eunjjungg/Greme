package com.shootit.greme.ui.view

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityChallengeInfoBinding
import com.shootit.greme.model.ChallengeInfo
import com.shootit.greme.model.ChallengeInfoImg
import com.shootit.greme.model.ChallengeInfoParcelData
import com.shootit.greme.ui.adapter.ChallengeInfoRecyclerAdapter
import com.shootit.greme.viewmodel.ChallengeInfoViewModel

class ChallengeInfoActivity :
    BaseActivity<ActivityChallengeInfoBinding>(R.layout.activity_challenge_info) {

    override val viewModel by viewModels<ChallengeInfoViewModel> {
        ChallengeInfoViewModel.ChallengeInfoViewModelFactory("tmp")
    }

    enum class Status {
        Participate, Exception
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@ChallengeInfoActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        initRecyclerView()
        // TODO Status 변경 필요
        initFabByStatus(Status.Participate)
        setListenerToFab()
    }

    private fun initRecyclerView() {
        val transportedData: ChallengeInfoParcelData? = intent.getParcelableExtra<ChallengeInfoParcelData?>("ChallengeInfo")
        val adapter = ChallengeInfoRecyclerAdapter(resources)
        transportedData?.let {
            adapter.challengeInfo = ChallengeInfo(transportedData.title, transportedData.desc, transportedData.day, transportedData.isRegistered)
        }

        val imgData : MutableList<ChallengeInfoImg> = mutableListOf(
            ChallengeInfoImg("123","123","123"),
            ChallengeInfoImg("123","123","123"),
            ChallengeInfoImg("123","123","123"),
            ChallengeInfoImg("123","123","123"),
            ChallengeInfoImg("123","123","123"),
            ChallengeInfoImg("123","123","123"),
            ChallengeInfoImg("https://exchange-data-s3-bucket.s3.ap-northeast-2.amazonaws.com/profile/image.png",null,null),
        )
        adapter.imgDataList = imgData
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initFabByStatus(status: Status) {
        when (status) {
            Status.Participate -> {
                binding.fabStatus.apply {
                    backgroundTintList = ColorStateList.valueOf(
                        resources.getColor(R.color.challenge_info_exception)
                    )
                    text = resources.getString(R.string.challenge_info_exception)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_plus, null)
                }
            }
            Status.Exception -> {
                binding.fabStatus.apply {
                    backgroundTintList = ColorStateList.valueOf(
                        resources.getColor(R.color.challenge_info_participate)
                    )
                    text = resources.getString(R.string.challenge_info_participate)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_plus, null)
                }
            }
        }
    }

    private fun setListenerToFab() {
        binding.fabStatus.setOnClickListener {
            // TODO 서버로 status 변환하는 코드
            // TODO 현재 status의 반대로 change하도록 코드 변경
            changeFabStatus(Status.Participate)
        }
    }

    fun changeFabStatus(transStatus: Status) {
        val colorException = resources.getColor(R.color.challenge_info_exception)
        val colorParticipate = resources.getColor(R.color.challenge_info_participate)

        when (transStatus) {
            Status.Participate -> {
                binding.fabStatus.apply {
                    colorTransitionAnim(from = colorException, to = colorParticipate)
                    text = resources.getString(R.string.challenge_info_exception)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_plus, null)
                }
            }
            Status.Exception -> {
                binding.fabStatus.apply {
                    colorTransitionAnim(from = colorParticipate, to = colorException)
                    text = resources.getString(R.string.challenge_info_participate)
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_plus, null)
                }
            }
        }
    }

    private fun colorTransitionAnim(from: Int, to: Int) {
        val anim = ValueAnimator.ofArgb(from, to).apply {
            duration = 1000 * 1
            addUpdateListener {
                binding.fabStatus.setBackgroundColor(it.animatedValue as Int)
            }
        }.start()
    }

}