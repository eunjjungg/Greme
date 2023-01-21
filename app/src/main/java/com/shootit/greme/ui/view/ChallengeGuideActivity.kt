package com.shootit.greme.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}