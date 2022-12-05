package com.shootit.greme.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VD: ViewDataBinding>(resource: Int) : AppCompatActivity() {
    /*@LayoutRes
    abstract fun getLayoutRes(): Int*/

    abstract val viewModel: ViewModel

    val binding by lazy {
        DataBindingUtil.setContentView(this, resource) as VD
    }

    /*val itemDao by lazy {
        AppDatabase.getDatabase(this).itemDao()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        binding
        super.onCreate(savedInstanceState)
        initViewModel(viewModel)
        onCreateAction()
    }

    abstract fun initViewModel(viewModel: ViewModel)
    abstract fun onCreateAction()
}