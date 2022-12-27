package com.shootit.greme.ui.view

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.shootit.greme.R
import com.shootit.greme.base.BaseActivity
import com.shootit.greme.databinding.ActivityMainBinding
import com.shootit.greme.repository.LoginRepository
import com.shootit.greme.ui.fragment.DiaryFragment
import com.shootit.greme.ui.fragment.HomeFragment
import com.shootit.greme.ui.fragment.SearchFragment
import com.shootit.greme.ui.fragment.SettingFragment
import com.shootit.greme.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel by lazy {
        ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(LoginRepository.getInstance(application)!!)
        ).get(MainViewModel::class.java)
    }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@MainActivity
        binding.viewModel = this.viewModel
    }

    private val fl: FrameLayout by lazy {
        binding.navFl
    }
    private val bn: BottomNavigationView by lazy {
        binding.navBn
    }

    override fun onCreateAction() {
        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()

        // 하단바 아이콘 원래 색깔로 보여주기 위함
        bn.itemIconTintList = null
        bn.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.menu_home -> {

                    loadFragment(HomeFragment())
                    true
                }
                R.id.menu_diary -> {
                    loadFragment(DiaryFragment())
                    true
                }
                R.id.menu_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.menu_setting -> {
                    loadFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }
    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }
}