package com.shootit.greme.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.shootit.greme.R
import com.shootit.greme.repository.LoginRepository
import com.shootit.greme.ui.fragment.HomeFragment

class MainViewModel(private val tmp: String): ViewModel() {

    enum class MainFragmentEnum(val id: Int, val text: String) {
        HomeFragment(R.id.menu_home, "챌린지"),
        DiaryFragment(R.id.menu_diary, "다이어리"),
        SearchFragment(R.id.menu_search, "검색"),
        SettingFragment(R.id.menu_setting, "환경설정"),
    }

    var currentPage = MutableLiveData<MainFragmentEnum>(MainFragmentEnum.HomeFragment)
    fun setCurrentPageById(id: Int) {
        currentPage.value = when(id) {
            R.id.menu_home -> {
                MainFragmentEnum.HomeFragment
            }
            R.id.menu_diary -> {
                MainFragmentEnum.DiaryFragment
            }
            R.id.menu_search -> {
                MainFragmentEnum.SearchFragment
            }
            R.id.menu_setting -> {
                MainFragmentEnum.SettingFragment
            }
            else -> {
                MainFragmentEnum.SettingFragment
            }
        }
    }


    class MainViewModelFactory(private val tmp: String)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(tmp) as T
            }
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }


}