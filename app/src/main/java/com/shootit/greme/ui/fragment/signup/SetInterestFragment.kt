package com.shootit.greme.ui.fragment.signup

import androidx.fragment.app.activityViewModels
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetInterestBinding
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface
import com.shootit.greme.ui.view.InterestButton
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

}