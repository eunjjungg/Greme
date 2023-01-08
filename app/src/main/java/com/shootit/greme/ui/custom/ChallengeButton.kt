package com.shootit.greme.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.shootit.greme.R
import com.shootit.greme.databinding.ButtonChallengeBinding
import com.shootit.greme.ui.`interface`.ChallengeMenuButtonClickInterface

class ChallengeButton : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private val binding : ButtonChallengeBinding = ButtonChallengeBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    lateinit var listener: ChallengeMenuButtonClickInterface


    private fun init(context: Context, attrs: AttributeSet){
        binding
        setAttrs(context, attrs)
        setClickListener()


    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChallengeButton)
            val icon = typedArray.getResourceId(R.styleable.InterestButton_interest_icon, R.drawable.ic_challenge_main)
            binding.icon.setImageDrawable(AppCompatResources.getDrawable(context, icon))
            binding.description.text = typedArray.getString(R.styleable.InterestButton_interest_description)

            typedArray.recycle()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun setClickListener() {
        binding.menu.setOnClickListener {
            if (this::listener.isInitialized) {
                listener.challengeMenuOnClick(binding.description.text.toString())
            }
        }
    }

    fun setCustomListener(listener: ChallengeMenuButtonClickInterface) {
        this.listener = listener
    }

    fun getMenuIconView(): ImageView {
        return binding.icon
    }

    fun getMenuDescView(): TextView {
        return binding.description
    }

}