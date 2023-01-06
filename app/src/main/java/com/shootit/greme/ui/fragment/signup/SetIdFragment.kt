package com.shootit.greme.ui.fragment.signup

import android.content.res.ColorStateList
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetIdBinding
import com.shootit.greme.util.EncryptedSpfObject
import com.shootit.greme.viewmodel.SignUpViewModel
import com.google.android.material.R as googleR

class SetIdFragment : BaseFragment<FragmentSetIdBinding>(R.layout.fragment_set_id) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.viewModel = viewModel
        println(viewModel.interestList.toString())
        setEditText()
        setErrorOrGuideText()
        setButtonListener()
        setButtonNextEnabled(false)
    }

    private fun setButtonListener() {
        binding.btnNext.setOnClickListener {
            viewModel.makeAccount(EncryptedSpfObject.getEncryptedSpf(binding.root.context)) { result: Boolean ->
                if(result) {
                    viewModel.fragmentTransition.value = SignUpViewModel.SIGNUP_FRAGMENT.INTEREST
                } else {
                    Toast.makeText(binding.root.context, "사용자 등록에 실패했습니다.\n잠시 후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setEditText() {
        binding.etId.editText?.doOnTextChanged { text, start, before, count ->
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
                if(it == R.string.signup_ok) {
                    setButtonNextEnabled(true)
                } else {
                    setButtonNextEnabled(false)
                }
            }
            if(viewModel.guideText.value == null) {
                setButtonNextEnabled(false)
            }
        }

    }

    private fun setButtonNextEnabled(isEnable: Boolean) {
        binding.btnNext.isEnabled = isEnable
        if (isEnable) {
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(
                resources.getColor(R.color.signup_button)
            )
        } else {
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(
                resources.getColor(R.color.status_bar)
            )
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


}