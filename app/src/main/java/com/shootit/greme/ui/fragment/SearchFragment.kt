package com.shootit.greme.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentSearchBinding
import com.shootit.greme.model.SearchData
import com.shootit.greme.ui.adapter.SearchAdapter

class SearchFragment : Fragment(R.layout.fragment_search) {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentSearchBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    private lateinit var rvSearch: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<SearchData>()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvSearch = binding.rvSearch
        searchView = binding.searchView

        rvSearch.setHasFixedSize(true)
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                rvSearch.layoutManager = GridLayoutManager(context, 3)
                addDataToList()
                adapter = SearchAdapter(mList)
                rvSearch.adapter = adapter
                return false
            }
        })


        return root
    }
    private fun addDataToList(){
        mList.add(SearchData(R.drawable.ex_img))
        mList.add(SearchData(R.drawable.ex_img))
        mList.add(SearchData(R.drawable.ex_img2))
        mList.add(SearchData(R.drawable.ex_img2))
        mList.add(SearchData(R.drawable.ex_img))
        mList.add(SearchData(R.drawable.ex_img2))

    }

}