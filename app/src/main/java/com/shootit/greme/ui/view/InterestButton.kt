package com.shootit.greme.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.shootit.greme.R
import com.shootit.greme.databinding.ButtonInterestBinding

class InterestButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding : ButtonInterestBinding = ButtonInterestBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        binding
        setAttrs(context, attrs)
    }

    fun setAttrs(context: Context, attrs: AttributeSet) {

        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InterestButton)
            val icon = typedArray.getResourceId(R.styleable.InterestButton_interest_icon, R.drawable.ic_recycle)
            binding.icon.setImageDrawable(AppCompatResources.getDrawable(context, icon))
            binding.description.text = typedArray.getString(R.styleable.InterestButton_interest_description)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }
}