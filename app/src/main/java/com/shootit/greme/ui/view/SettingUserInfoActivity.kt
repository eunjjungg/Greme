package com.shootit.greme.ui.view

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivitySettingUserInfoBinding
import com.shootit.greme.model.GENDER
import com.shootit.greme.repository.SettingRepository
import com.shootit.greme.ui.`interface`.InterestButtonClickInterface
import com.shootit.greme.ui.custom.InterestButton
import com.shootit.greme.viewmodel.SettingUserInfoViewModel
import com.shootit.greme.viewmodel.SignUpViewModel

/**
 * 넘겨받는 데이터 : none
 */

class SettingUserInfoActivity :
    BaseActivity<ActivitySettingUserInfoBinding>(R.layout.activity_setting_user_info) {
    override val viewModel by viewModels<SettingUserInfoViewModel> {
        SettingUserInfoViewModel.SettingUserInfoViewModelFactory(
            SettingRepository.getInstance()!!
        )
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@SettingUserInfoActivity
        binding.vm = this.viewModel
    }

    override fun onCreateAction() {
        //ViewCompat.setTransitionName(binding.tvPageName, "pageName")
        initData()
        initDropDownAdapter()
        setInterestButtonListener()
        setGenderButtonListener(listOf(binding.btnMale, binding.btnFemale, binding.btnWhatever))
        setPurposeDropDownListener()
        setRegionDropDownListener()
        setNextButtonListener()
    }

    private fun initData() {
        viewModel.getInitData(
            initInterest = { initInterestButton(it) },
            initGender = { initGenderButton(it) },
            initArea = { initRegionDropDown(it) },
            initPurpose = { initPurposeDropDown(it) },
            errorCase = { makeSnackBar("접속이 불안정합니다. 잠시 후 다시 시도해주세요.") }
        )
    }

    private fun initInterestButton(interestList: List<SignUpViewModel.INTEREST>) {
        val interestButtons = listOf<InterestButton>(
            binding.btnEnergy,
            binding.btnUpCycling,
            binding.btnEcoProduct,
            binding.btnVeganFood,
            binding.btnVeganCosmetic
        )
        interestList.forEach { interest ->
            interestButtons[interest.index].initButton(true)
        }
    }

    private fun initGenderButton(gender: GENDER) {
        when (gender) {
            GENDER.Male -> binding.btnMale.initButton(true)
            GENDER.Female -> binding.btnFemale.initButton(true)
            GENDER.Whatever -> binding.btnWhatever.initButton(true)
        }
    }

    private fun initDropDownAdapter() {
        val regions = resources.getStringArray(R.array.regionLarge)
        val purpose = resources.getStringArray(R.array.signup_purpose)
        ArrayAdapter(binding.root.context, R.layout.dropdown_item, regions)
            .also { binding.dropdownRegion.setAdapter(it) }
        ArrayAdapter(binding.root.context, R.layout.dropdown_item, purpose)
            .also { binding.dropdownPurpose.setAdapter(it) }
    }

    private fun initPurposeDropDown(purpose: String) {
        binding.dropdownPurpose.setText(purpose, false)
    }

    private fun initRegionDropDown(region: String) {
        binding.dropdownRegion.setText(region, false)
    }

    private fun setInterestButtonListener() {
        val interestButtons = listOf<InterestButton>(
            binding.btnEnergy,
            binding.btnUpCycling,
            binding.btnEcoProduct,
            binding.btnVeganFood,
            binding.btnVeganCosmetic
        )

        val listener: InterestButtonClickInterface = object : InterestButtonClickInterface {
            override fun interestButtonOnClick(title: String, isClicked: Boolean) {
                viewModel.interestSelected(title, isClicked)
            }
        }

        interestButtons.forEach {
            it.setButtonListener(listener)
        }
    }

    private fun setGenderButtonListener(genderButtons: List<InterestButton>) {
        val listener: InterestButtonClickInterface = object : InterestButtonClickInterface {
            override fun interestButtonOnClick(title: String, isClicked: Boolean) {
                val initButton: (List<InterestButton>) -> Unit = {
                    it.forEach { button -> button.initButton(false) }
                }
                if (isClicked) {
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
                } else {
                    val setNull: (GENDER) -> Unit = {
                        if (viewModel.gender == it)
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


    private fun setPurposeDropDownListener() {
        binding.dropdownPurpose.setOnItemClickListener { adapterView, view, position, rowId ->
            viewModel.purpose = adapterView.getItemAtPosition(position).toString()
        }
    }

    private fun setRegionDropDownListener() {
        binding.dropdownRegion.setOnItemClickListener { adapterView, view, position, rowId ->
            viewModel.region = adapterView.getItemAtPosition(position).toString()
        }
    }

    private fun setNextButtonListener() {
        binding.btnNext.setOnClickListener {
            viewModel.saveUserInfo(
                { makeSnackBar(it) },
                {
                    if (it) {
                        makeSnackBar("프로필 업데이트 완료!")
                    } else {
                        makeSnackBar("저장에 실패하였습니다. 다시 시도해주세요.")
                    }
                }
            )
        }
    }

    private fun makeSnackBar(message: String) {
        val snackbar = Snackbar.make(binding.view, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("확인", View.OnClickListener {
            snackbar.dismiss()
        })
        snackbar.show()
    }
}