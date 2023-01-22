package com.shootit.greme.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.shootit.greme.R
import com.shootit.greme.databinding.LayoutChallengeSummaryBinding
import com.shootit.greme.ui.`interface`.ChallengeSummaryClickInterface

class ChallengeSummary : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    enum class ChallengeSummaryDescType(val desc: String, var content: String) {
        Review("인기 챌린지 후기 보러가기", "#"),
        Main("이번 주 대표 챌린지는", "#"),
        My("내가 참여한 챌린지 보러가기", "#")
    }

    private val binding: LayoutChallengeSummaryBinding = LayoutChallengeSummaryBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    lateinit var listener: ChallengeSummaryClickInterface

    private fun init(context: Context, attrs: AttributeSet){
        binding
        setAttrs(context, attrs)
        setClickListener()
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChallengeSummary)
            val icon = typedArray.getResourceId(R.styleable.InterestButton_interest_icon, R.drawable.ic_challenge_my)
            binding.icon.setImageDrawable(AppCompatResources.getDrawable(context, icon))
            typedArray.apply {
                binding.tvDesc.text = getString(R.styleable.ChallengeSummary_summary_description)
                binding.tvName.text = getString(R.styleable.ChallengeSummary_summary_name)
            }

            typedArray.recycle()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun setClickListener() {
        binding.challengeSummary.setOnClickListener {
            if (this::listener.isInitialized) {
                listener.challengeSummaryOnClick()
            }
        }
    }

    fun setCustomListener(listener: ChallengeSummaryClickInterface) {
        this.listener = listener
    }

    fun setContent(type: ChallengeSummaryDescType) {
        setProperIconByType(type)
        binding.tvDesc.text = type.desc
        binding.tvName.text = type.content
    }

    private fun setProperIconByType(type: ChallengeSummaryDescType) {
        val icon = when(type) {
            ChallengeSummaryDescType.Review -> R.drawable.ic_challenge_my
            ChallengeSummaryDescType.My -> R.drawable.ic_challenge_my
            ChallengeSummaryDescType.Main -> R.drawable.ic_challenge_main
        }
        binding.icon.setImageDrawable(ResourcesCompat.getDrawable(resources, icon, null))
    }
}