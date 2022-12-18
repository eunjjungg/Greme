package com.shootit.greme.ui.fragment.signup

import androidx.fragment.app.activityViewModels
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentSetInterestBinding
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface
import com.shootit.greme.viewmodel.SignUpViewModel

class SetInterestFragment : BaseFragment<FragmentSetInterestBinding>(R.layout.fragment_set_interest) {

    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.viewModel = viewModel
        binding.apply {
            setInterestButtonListener()
        }

    }

    private fun FragmentSetInterestBinding.setInterestButtonListener() {
        val listener: InterestButtonClickInterface = object : InterestButtonClickInterface {
            override fun interestButtonOnClick(title: String, isClicked: Boolean) {
                viewModel?.interestSelected(title, isClicked)
            }
        }

        btnEnergy.setButtonListener(listener)
        btnUpCycling.setButtonListener(listener)
        btnEcoProduct.setButtonListener(listener)
        btnVeganFood.setButtonListener(listener)
        btnVeganCosmetic.setButtonListener(listener)
    }
}