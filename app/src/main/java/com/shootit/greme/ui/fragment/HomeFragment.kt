package com.shootit.greme.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentHomeBinding
import com.shootit.greme.ui.`interface`.ChallengeMenuButtonClickInterface
import com.shootit.greme.ui.custom.ChallengeSummary
import com.shootit.greme.ui.view.ChallengeActivity
import com.shootit.greme.viewmodel.ChallengeHomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel by viewModels<ChallengeHomeViewModel> {
        ChallengeHomeViewModel.ChallengeHomeViewModelFactory("tmp")
    }

    override fun initView() {
        binding.initMenuButtons()
        binding.initSummaryButtons()
    }

    private fun FragmentHomeBinding.initSummaryButtons() {
        btnSummaryTop.setContent(ChallengeSummary.ChallengeSummaryDescType.Review.also { it.content = "#투명페트병_생수_이용하기" })
        btnSummaryBottom.setContent(ChallengeSummary.ChallengeSummaryDescType.Main.also { it.content = "#가까운_거리는_걸어다니기" })
    }

    private fun FragmentHomeBinding.initMenuButtons() {
        btnMain.setCustomListener(object : ChallengeMenuButtonClickInterface {
            override fun challengeMenuOnClick(title: String) {
                onMenuClick(title)
            }
        })
    }

    private fun onMenuClick(title: String) {
        fun String.findChallengeMenuByText(): ChallengeMenu {
            return when(this) {
                ChallengeMenu.PopularChallengeMenu.text -> ChallengeMenu.PopularChallengeMenu
                ChallengeMenu.MyChallengeMenu.text -> ChallengeMenu.MyChallengeMenu
                ChallengeMenu.DiaryMenu.text -> ChallengeMenu.DiaryMenu
                else -> ChallengeMenu.GuideMenu
            }
        }

        when (title.findChallengeMenuByText()) {
            ChallengeMenu.MyChallengeMenu -> {
                Intent(binding.root.context, ChallengeActivity::class.java).also {
                    val pair: androidx.core.util.Pair<View, String> = androidx.core.util.Pair(binding.btnMain.getMenuIconView() as View, "icon")
                    val optionPair = ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeFragment.activity as Activity, pair)
                    startActivity(it, optionPair.toBundle())
                }
            }
            ChallengeMenu.DiaryMenu -> {

            }
            ChallengeMenu.GuideMenu -> {

            }
            ChallengeMenu.PopularChallengeMenu -> {

            }
        }
    }

    enum class ChallengeMenu(val text: String) {
        PopularChallengeMenu("인기 챌린지"),
        MyChallengeMenu("챌린지"),
        DiaryMenu("챌린지 도전"),
        GuideMenu("사용안내")
    }
}