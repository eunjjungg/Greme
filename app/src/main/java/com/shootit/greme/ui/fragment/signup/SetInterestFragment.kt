package com.shootit.greme.ui.fragment.signup

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetInterestBinding
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface
import com.shootit.greme.ui.custom.InterestButton
import com.shootit.greme.viewmodel.SignUpViewModel

class SetInterestFragment :
    BaseFragment<FragmentSetInterestBinding>(R.layout.fragment_set_interest) {

    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.viewModel = viewModel
        val interestButtons: List<InterestButton> = binding.run {
            listOf<InterestButton>(
                btnEnergy, btnUpCycling, btnEcoProduct, btnVeganFood, btnVeganCosmetic
            )
        }

        setIdView()
        initButton()
        initInterestButton(interestButtons)
        binding.setInterestButtonListener(interestButtons)
    }

    private fun FragmentSetInterestBinding.setInterestButtonListener(interestButtons: List<InterestButton>) {
        val listener: InterestButtonClickInterface = object : InterestButtonClickInterface {
            override fun interestButtonOnClick(title: String, isClicked: Boolean) {
                viewModel?.interestSelected(title, isClicked)
            }
        }

        interestButtons.forEach {
            it.setButtonListener(listener)
        }
    }

    private fun initInterestButton(interestButtons: List<InterestButton>) {
        interestButtons.forEachIndexed { index, interestButton ->
            interestButton.initButton(viewModel.interestList[SignUpViewModel.INTEREST.values()[index].index])
        }
    }

    private fun setIdView() {
        binding.tvWelcome.text =
            String.format(resources.getString(R.string.signup_interest_welcome), viewModel.id)
    }

    private fun initButton() {
        binding.btnNext.setOnClickListener {
            if(selectInterestAtLeastOne()) {
                viewModel.setInterest { result: Boolean ->
                    if(!result) {
                        Toast.makeText(binding.root.context, "사용자 등록에 실패했습니다.\n잠시 후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.finishSignUp()
                    }
                }
            } else {
                Toast.makeText(binding.root.context, "하나 이상은 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMoreInfo.setOnClickListener {
            if(selectInterestAtLeastOne()) {
                viewModel.transitionToMoreInfo()
            } else {
                Toast.makeText(binding.root.context, "하나 이상은 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectInterestAtLeastOne(): Boolean {
        var isSelectedAtLeastOne = false
        viewModel.interestList.forEach {
            if (it)
                isSelectedAtLeastOne = true
        }
        return isSelectedAtLeastOne
    }
}