package com.shootit.greme.ui.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.shootit.greme.R
import com.shootit.greme.base.BaseFragment
import com.shootit.greme.databinding.FragmentCheckTermsBinding
import com.shootit.greme.util.EncryptedSpfObject
import com.shootit.greme.viewmodel.SignUpViewModel

class CheckTermsFragment : BaseFragment<FragmentCheckTermsBinding>(R.layout.fragment_check_terms) {
    override val viewModel by activityViewModels<SignUpViewModel>()

    override fun initView() {
        binding.vm = viewModel
        binding.apply {  }

        setWebView()
        setButtonListener()
    }

    private fun setWebView() {
        binding.wvTerm.settings.javaScriptEnabled = true
        val data = "file:///android_asset/terms.html"
        binding.wvTerm.loadUrl(data)
        binding.wvTerm.webViewClient = WebViewClient()
    }

    private fun setButtonListener() {
        binding.btnNext.setOnClickListener {
            viewModel.fragmentTransition.value = SignUpViewModel.SIGNUP_FRAGMENT.ID
        }
    }

}