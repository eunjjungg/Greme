package com.shootit.greme.ui.fragment.signup

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentAdditionalInfoBinding
import com.shootit.greme.viewmodel.SignUpViewModel

class AdditionalInfoFragment :
    BaseFragment<FragmentAdditionalInfoBinding>(R.layout.fragment_additional_info) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.vm = viewModel
        binding.apply { }
        initDropDown()
        setDropDownListener()
    }

    private fun initDropDown() {
        val regions = resources.getStringArray(R.array.regionLarge)
        val purpose = resources.getStringArray(R.array.signup_purpose)
        ArrayAdapter(this.requireContext(), R.layout.dropdown_item, regions)
            .also { binding.dropdownRegion.setAdapter(it) }
        ArrayAdapter(this.requireContext(), R.layout.dropdown_item, purpose)
            .also { binding.dropdownPurpose.setAdapter(it) }


    }

    private fun setDropDownListener() {
        binding.dropdownPurpose.setOnItemClickListener { adapterView, view, position, rowId ->
            Log.d("tag", "position: $position, rowId:$rowId, string: ${adapterView.getItemAtPosition(position)}")
        }

        binding.dropdownRegion.setOnItemClickListener { adapterView, view, position, rowId ->
            Log.d("tag", "position: $position, rowId:$rowId, string: ${adapterView.getItemAtPosition(position)}")
        }
    }

}