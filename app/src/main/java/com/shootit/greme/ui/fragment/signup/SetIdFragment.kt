package com.shootit.greme.ui.fragment.signup

import android.content.res.ColorStateList
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetIdBinding
import com.shootit.greme.viewmodel.SignUpViewModel
import com.google.android.material.R as googleR

class SetIdFragment : BaseFragment<FragmentSetIdBinding>(R.layout.fragment_set_id) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.viewModel = viewModel
        binding.apply {

        }
        println(viewModel.interestList.toString())
        tmp()
        binding.buttonNext.isEnabled = false
        setErrorOrGuideText()
    }

    private fun tmp() {
        binding.etId.editText?.doOnTextChanged { text, start, before, count ->
            Log.d("ccheck", "input: <${text.toString()}>")
            viewModel.isIdProper(text.toString())
        }
    }

    private fun setErrorOrGuideText() {
        viewModel.errorText.observe(this) {
            // when error message need to show
            viewModel.errorText.value?.let {
                binding.etId.setErrorMSG(getString(it))
            }
        }

        // when guide message need to show
        viewModel.guideText.observe(this) {
            viewModel.guideText.value?.let {
                binding.etId.setProperMSG(getString(it))
            }
        }

    }

    private fun TextInputLayout.setErrorMSG(errorMessage: String) {
        this.apply {
            ColorStateList.valueOf(
                resources.getColor(R.color.signup_et_error)
            ).also {
                boxStrokeErrorColor = it
                setErrorTextColor(it)
                setErrorIconTintList(it)
                error = errorMessage
                errorIconDrawable = resources.getDrawable(googleR.drawable.mtrl_ic_error)
            }
        }
    }

    private fun TextInputLayout.setProperMSG(guideMessage: String) {
        this.apply {
            ColorStateList.valueOf(
                resources.getColor(R.color.signup_et_no_error)
            ).also {
                boxStrokeErrorColor = it
                setErrorTextColor(it)
                setErrorIconTintList(it)
                error = guideMessage
                errorIconDrawable = resources.getDrawable(R.drawable.ic_check)
            }
        }
    }

    private fun FragmentSetIdBinding.setButtonEnable(isEnable: Boolean) {
        this.buttonNext.isEnabled = isEnable
    }

}