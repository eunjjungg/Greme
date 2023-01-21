package com.shootit.greme.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentHomeBinding
import com.shootit.greme.model.FeedDetail
import com.shootit.greme.repository.ChallengeRepository
import com.shootit.greme.ui.`interface`.ChallengeMenuButtonClickInterface
import com.shootit.greme.ui.`interface`.ChallengeSummaryClickInterface
import com.shootit.greme.ui.custom.ChallengeSummary
import com.shootit.greme.ui.view.ChallengeActivity
import com.shootit.greme.ui.view.ChallengeGuideActivity
import com.shootit.greme.ui.view.ChallengeInfoActivity
import com.shootit.greme.ui.view.MainActivity
import com.shootit.greme.viewmodel.ChallengeHomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel by viewModels<ChallengeHomeViewModel> {
        ChallengeHomeViewModel.ChallengeHomeViewModelFactory(
            ChallengeRepository.getInstance()!!
        )
    }

    override fun initView() {
        binding.initMenuButtons()
        binding.initSummaryButtons()
    }

    private fun FragmentHomeBinding.initSummaryButtons() {
        val popularCompletion: (FeedDetail?) -> Unit = { feedDetail ->
            if (feedDetail == null) {
                btnSummaryBottom.setContent(ChallengeSummary.ChallengeSummaryDescType.Main.also { it.content = "network error" })
                makeSnackBar("접속이 불안정합니다. 잠시 후 다시 시도해주세요.")
            } else {
                btnSummaryBottom.setContent(ChallengeSummary.ChallengeSummaryDescType.Main.also { it.content = feedDetail.title })

                Listener@
                btnSummaryBottom.setCustomListener(object : ChallengeSummaryClickInterface {
                    override fun challengeSummaryOnClick() {
                        viewModel.getParcelData(id = feedDetail.id) { parcel ->
                            Intent(binding.root.context, ChallengeInfoActivity::class.java).also {
                                it.putExtra("ChallengeInfo", parcel)
                                startActivity(it)
                            }
                        }
                    }
                })
            }
        }
        val participationCompletion: (FeedDetail?) -> Unit = { feedDetail ->
            if (feedDetail == null) {
                btnSummaryTop.setContent(ChallengeSummary.ChallengeSummaryDescType.My.also {
                    it.content = "현재 참여 중인 챌린지가 없어요! 챌린지 참여하러 가기"
                })

                Listener@
                btnSummaryTop.setCustomListener(object : ChallengeSummaryClickInterface {
                    override fun challengeSummaryOnClick() {
                        Intent(binding.root.context, ChallengeActivity::class.java).also { startActivity(it) }
                    }
                })
            } else {
                btnSummaryTop.setContent(ChallengeSummary.ChallengeSummaryDescType.My.also { it.content = feedDetail.title })

                Listener@
                btnSummaryTop.setCustomListener(object : ChallengeSummaryClickInterface {
                    override fun challengeSummaryOnClick() {
                        viewModel.getParcelData(id = feedDetail.id) { parcel ->
                            Intent(binding.root.context, ChallengeInfoActivity::class.java).also {
                                it.putExtra("ChallengeInfo", parcel)
                                startActivity(it)
                            }
                        }
                    }
                })
            }
        }

         viewModel.getHomeFeedData(participationCompletion = participationCompletion, popularCompletion = popularCompletion)
    }

    private fun FragmentHomeBinding.initMenuButtons() {
        val btnList = listOf(btnGuide, btnMain, btnPopular, btnMy)
        btnList.forEach {
            it.setCustomListener(object : ChallengeMenuButtonClickInterface {
                override fun challengeMenuOnClick(title: String) {
                    onMenuClick(title)
                }
            })
        }
    }

    private fun onMenuClick(title: String) {
        fun String.findChallengeMenuByText(): ChallengeMenu {
            return when(this) {
                ChallengeMenu.PopularChallengeMenu.text -> ChallengeMenu.PopularChallengeMenu
                ChallengeMenu.MyChallengeMenu.text -> ChallengeMenu.MyChallengeMenu
                ChallengeMenu.DiaryMenu.text -> ChallengeMenu.DiaryMenu
                ChallengeMenu.GuideMenu.text -> ChallengeMenu.GuideMenu
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
                val activity = activity as MainActivity
                activity.moveToDiaryFragment()
            }
            ChallengeMenu.GuideMenu -> {
                Intent(binding.root.context, ChallengeGuideActivity::class.java).also {
                    val pair: androidx.core.util.Pair<View, String> = androidx.core.util.Pair(binding.btnGuide.getMenuIconView() as View, "icon")
                    val optionPair = ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeFragment.activity as Activity, pair)
                    startActivity(it, optionPair.toBundle())
                }
            }
            ChallengeMenu.PopularChallengeMenu -> {
                viewModel.getPopularChallenge { parcel ->
                    Intent(binding.root.context, ChallengeInfoActivity::class.java).also {
                        it.putExtra("ChallengeInfo", parcel)
                        startActivity(it)
                    }
                }
            }
        }
    }

    private fun makeSnackBar(message: String) {
        val snackbar = Snackbar.make(binding.root.rootView, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("확인", View.OnClickListener {
            snackbar.dismiss()
        })
        snackbar.show()
    }

    enum class ChallengeMenu(val text: String) {
        PopularChallengeMenu("인기 챌린지"),
        MyChallengeMenu("챌린지"),
        DiaryMenu("챌린지 도전"),
        GuideMenu("사용안내")
    }
}