package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.shootit.greme.R
import com.shootit.greme.databinding.ActivityChallengeGuideBinding
import com.shootit.greme.ui.view.compose.ComposeCards
import com.shootit.greme.ui.view.compose.EmptyView
import com.shootit.greme.viewmodel.ChallengeGuideViewModel

class ChallengeGuideActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_challenge_guide) as ActivityChallengeGuideBinding
    }

    private val viewModel by viewModels<ChallengeGuideViewModel> {
        ChallengeGuideViewModel.ChallengeGuideViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
        ViewCompat.setTransitionName(binding.icon, "icon")

        binding.composeView.setContent {
            GuideTopActivity(viewModel)
        }
    }
}

@Composable
fun GuideTopActivity(viewModel: ChallengeGuideViewModel) {
    Column(modifier = Modifier) {
        ComposeCards(viewModel = viewModel)
        EmptyView()

    }
}
