package com.shootit.greme.ui.fragment.signup

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentAdditionalInfoBinding
import com.shootit.greme.model.GENDER
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface
import com.shootit.greme.ui.custom.InterestButton
import com.shootit.greme.viewmodel.SignUpViewModel

class AdditionalInfoFragment :
    BaseFragment<FragmentAdditionalInfoBinding>(R.layout.fragment_additional_info) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.vm = viewModel
        binding.apply { }
        initDropDown()
        initGenderButton()
        initNextButton()
    }

    private fun initDropDown() {
        setDropDownAdapter()
        setDropDownListener()
    }

    private fun setDropDownAdapter() {
        val regions = resources.getStringArray(R.array.regionLarge)
        val purpose = resources.getStringArray(R.array.signup_purpose)
        ArrayAdapter(this.requireContext(), R.layout.dropdown_item, regions)
            .also { binding.dropdownRegion.setAdapter(it) }
        ArrayAdapter(this.requireContext(), R.layout.dropdown_item, purpose)
            .also { binding.dropdownPurpose.setAdapter(it) }
    }

    private fun setDropDownListener() {
        binding.dropdownPurpose.setOnItemClickListener { adapterView, view, position, rowId ->
            viewModel.purpose = adapterView.getItemAtPosition(position).toString()
            Log.d("tag", "position: $position, rowId:$rowId, string: ${adapterView.getItemAtPosition(position)}")
        }

        binding.dropdownRegion.setOnItemClickListener { adapterView, view, position, rowId ->
            viewModel.region = adapterView.getItemAtPosition(position).toString()
            Log.d("tag", "position: $position, rowId:$rowId, string: ${adapterView.getItemAtPosition(position)}")
        }
    }

    private fun initGenderButton() {
        setGenderButtonListener(
            listOf(
                binding.btnMale,
                binding.btnFemale,
                binding.btnWhatever
            )
        )
    }

    private fun setGenderButtonListener(genderButtons: List<InterestButton>) {
        val listener: InterestButtonClickInterface = object : InterestButtonClickInterface {
            override fun interestButtonOnClick(title: String, isClicked: Boolean) {
                val initButton: (List<InterestButton>) -> Unit = {
                    it.forEach { button -> button.initButton(false) }
                }
                if(isClicked) {
                    when (title) {
                        GENDER.Male.text -> {
                            initButton(listOf(binding.btnFemale, binding.btnWhatever))
                            viewModel.gender = GENDER.Male
                        }
                        GENDER.Female.text -> {
                            initButton(listOf(binding.btnMale, binding.btnWhatever))
                            viewModel.gender = GENDER.Female
                        }
                        else -> {
                            initButton(listOf(binding.btnMale, binding.btnFemale))
                            viewModel.gender = GENDER.Whatever
                        }
                    }
                }
                else {
                    val setNull: (GENDER) -> Unit = {
                        if(viewModel.gender == it)
                            viewModel.gender = null
                    }
                    when (title) {
                        GENDER.Male.text -> {
                            setNull(GENDER.Male)
                        }
                        GENDER.Female.text -> {
                            setNull(GENDER.Female)
                        }
                        else -> {
                            setNull(GENDER.Whatever)
                        }
                    }
                }
            }
        }

        genderButtons.forEach {
            it.setButtonListener(listener)
        }
    }

    private fun initNextButton() {
        binding.btnNext.setOnClickListener {
            if (viewModel.gender != null && viewModel.purpose.isNotBlank() && viewModel.region.isNotBlank()) {
                viewModel.setInterest { result: Boolean ->
                    if(!result) {
                        Toast.makeText(binding.root.context, "사용자 등록에 실패했습니다.\n잠시 후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.setAdditionalInfo { result: Boolean ->
                            if(!result) {
                                Toast.makeText(binding.root.context, "사용자 등록에 실패했습니다.\n잠시 후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.finishSignUp()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(binding.root.context, "모든 항목을 다 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
