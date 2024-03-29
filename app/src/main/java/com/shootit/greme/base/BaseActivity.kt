package com.shootit.greme.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VD: ViewDataBinding>(@LayoutRes val layoutRes: Int) : AppCompatActivity() {
    /*@LayoutRes
    abstract fun getLayoutRes(): Int*/

    abstract val viewModel: ViewModel

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutRes) as VD
    }

    /*val itemDao by lazy {
        AppDatabase.getDatabase(this).itemDao()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
        initViewModel(viewModel)
        onCreateAction()
    }

    abstract fun initViewModel(viewModel: ViewModel)
    abstract fun onCreateAction()
}