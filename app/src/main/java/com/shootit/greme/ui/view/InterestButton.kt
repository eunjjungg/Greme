package com.shootit.greme.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.shootit.greme.R
import com.shootit.greme.databinding.ButtonInterestBinding
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface

class InterestButton : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }
    private var interestIsSelected: Boolean = false
    private val binding : ButtonInterestBinding = ButtonInterestBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    lateinit var listener: InterestButtonClickInterface

    private fun init(context: Context, attrs: AttributeSet){
        binding
        setAttrs(context, attrs)
        setClickListener(context)
    }

    private fun setAttrs(context: Context, attrs: AttributeSet) {
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InterestButton)
            val icon = typedArray.getResourceId(R.styleable.InterestButton_interest_icon, R.drawable.ic_recycle)
            binding.icon.setImageDrawable(AppCompatResources.getDrawable(context, icon))
            binding.description.text = typedArray.getString(R.styleable.InterestButton_interest_description)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }

    private fun setClickListener(context: Context) {
        binding.iconBg.setOnClickListener {
            interestIsSelected = !interestIsSelected
            when (interestIsSelected) {
                true -> binding.iconBg.background.setTint(ContextCompat.getColor(context, R.color.icon_bg_selected))
                else -> binding.iconBg.background.setTint(ContextCompat.getColor(context, R.color.icon_bg_unSelected))
            }
            listener?.interestButtonOnClick(binding.description.text.toString(), interestIsSelected)
        }
    }

    fun initButton(isSelected: Boolean) {
        interestIsSelected = isSelected
        when (interestIsSelected) {
            true -> binding.iconBg.background.setTint(ContextCompat.getColor(context, R.color.icon_bg_selected))
            else -> binding.iconBg.background.setTint(ContextCompat.getColor(context, R.color.icon_bg_unSelected))
        }
    }

    fun setButtonListener(listener: InterestButtonClickInterface) {
        this.listener = listener
    }
}