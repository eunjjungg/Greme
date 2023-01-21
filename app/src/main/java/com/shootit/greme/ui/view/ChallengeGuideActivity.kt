package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.shootit.greme.R
import com.shootit.greme.databinding.ActivityChallengeGuideBinding

class ChallengeGuideActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_challenge_guide) as ActivityChallengeGuideBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
        ViewCompat.setTransitionName(binding.icon, "icon")

        binding.composeView.setContent {
            GuideTopActivity()
        }
    }
}

@Composable
fun GuideTopActivity() {
    Text(text = "sample")
}