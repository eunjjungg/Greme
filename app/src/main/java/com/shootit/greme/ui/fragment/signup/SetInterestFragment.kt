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
        binding.initInterestButton(viewModel)
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

    private fun FragmentSetInterestBinding.initInterestButton(viewModel: SignUpViewModel) {
        btnEnergy.initButton(viewModel.interestList[SignUpViewModel.INTEREST.ENERGY.index])
        btnUpCycling.initButton(viewModel.interestList[SignUpViewModel.INTEREST.UP_CYCLING.index])
        btnEcoProduct.initButton(viewModel.interestList[SignUpViewModel.INTEREST.ECO_PRODUCT.index])
        btnVeganFood.initButton(viewModel.interestList[SignUpViewModel.INTEREST.VEGAN.index])
        btnVeganCosmetic.initButton(viewModel.interestList[SignUpViewModel.INTEREST.COSMETIC.index])
    }

}