package com.shootit.greme.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentSearchBinding
import com.shootit.greme.model.ResponseEntireDiaryData
import com.shootit.greme.model.ResponseOtherUserDiaryData
import com.shootit.greme.model.ResponseSearchData
import com.shootit.greme.model.SearchData
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.ui.adapter.SearchAdapter
import com.shootit.greme.ui.view.OtherUserDiaryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentSearchBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    private lateinit var rvSearch: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<SearchData>()
    val adapter = SearchAdapter(mList)

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

            // 검색창에 작성후 submitbutton을 누르면 검색어에 해당하는 다이어리 recyclerview 보여줌
            override fun onQueryTextSubmit(query: String?): Boolean {
                rvSearch.layoutManager = GridLayoutManager(context, 3)
                addDataToList()
                // adapter = SearchAdapter(mList)
                rvSearch.adapter = adapter

                // 검색으로 다이어리 조회 서버 연동
                Log.d("Network_Search", "searchDiary")
                ConnectionObject.getDiarySearchRetrofitService.searchDiary(searchView.query.toString()).enqueue(object : Callback<List<ResponseSearchData>>{
                    override fun onResponse(
                        call: Call<List<ResponseSearchData>>,
                        response: Response<List<ResponseSearchData>>
                    ) {
                        if (response.isSuccessful){
                            val data = response.body().toString()
                            Log.d("responsevalue", "searchDiary_response 값 => "+ data)
                            val data1 = response.code()
                            Log.d("status code", data1.toString())
                        }else{
                            // 이곳은 에러 발생할 경우 실행됨
                            Log.d("server err", response.errorBody()?.string().toString())
                            Log.d("Network_Search", "fail")
                        }
                    }

                    override fun onFailure(call: Call<List<ResponseSearchData>>, t: Throwable) {
                        Log.d("Network_Search", "error!")

                    }
                })
                Log.d("Network_Search", searchView.query.toString())
                return false
            }
        })

        // 다른 유저의 다이어리 조회 서버 연동(recyclerview 클릭시 실행되도록 수정해야함)
        binding.tvDescribe.setOnClickListener {
            Log.d("Network_OtherUser", "otherUserDiary")

            ConnectionObject.getDiarySearchRetrofitService.otherUserDiary(12).enqueue(object : Callback<ResponseOtherUserDiaryData> {
                override fun onResponse(
                    call: Call<ResponseOtherUserDiaryData>,
                    response: Response<ResponseOtherUserDiaryData>
                ) {
                    val data = response.code()
                    Log.d("status code", data.toString())
                    if (response.isSuccessful){
                        Log.d("Network_OtherUser", "success")
                        val data = response.body().toString()
                        Log.d("responsevalue", "otherUserDiary_response 값 => "+ data)
                    }else{
                        // 이곳은 에러 발생할 경우 실행됨
                        val data1 = response.code()
                        Log.d("status code", data1.toString())
                        val data2 = response.headers()
                        Log.d("header", data2.toString())
                        Log.d("server err", response.errorBody()?.string().toString())
                        Log.d("Network_OtherUser", "fail")
                    }
                }

                override fun onFailure(call: Call<ResponseOtherUserDiaryData>, t: Throwable) {
                    Log.d("Network_OtherUser", "error!")

                }
            })
        }
        adapter.setItemClickListener(object: SearchAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // 클릭 시 이벤트 작성
                val intent = Intent(getActivity(), OtherUserDiaryActivity::class.java)
                startActivity(intent)
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