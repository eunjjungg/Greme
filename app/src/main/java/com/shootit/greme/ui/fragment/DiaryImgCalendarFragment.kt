package com.shootit.greme.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentDiaryImgCalendarBinding
import com.shootit.greme.model.DiaryImgData
import com.shootit.greme.model.ResponseEntireDiaryData
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.ui.adapter.DiaryImgCalendarAdapter
import okio.utf8Size
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryImgCalendarFragment : Fragment() {
    lateinit var binding: FragmentDiaryImgCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        binding = FragmentDiaryImgCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 다이어리 전체 보기 서버 연동
        Log.d("Network_Entire", "entireDiary")
        ConnectionObject.getDiaryWriteRetrofitService.entireDiaryLook().enqueue(object :
            Callback<List<ResponseEntireDiaryData>> {
            override fun onResponse(
                call: Call<List<ResponseEntireDiaryData>>,
                response: Response<List<ResponseEntireDiaryData>>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    val num = data!!.count()
                    Log.d("responsevalue", "response 값 => "+ data)
                    Log.d("responsevalue", "num 값 => "+ num)
                    val datas = ArrayList<DiaryImgData>()
                    val imgs = ArrayList<String>()
                    for(i in 0..num-1){
                        val itemdata = response.body()?.get(i)
                        Log.d("responsevalue", "itemdata1_response 값 => "+ itemdata)
                        val num2 = itemdata!!.postInfos.count()
                        Log.d("responsevalue", "num2 값 => "+ num2)
                        for(j in 0..num2-1){
                            imgs.add(itemdata!!.postInfos!!.get(j).image)
                        }
                        datas.add(DiaryImgData(itemdata!!.date,imgs))
                    }
                    val itemdata1 = response.body()?.get(0)

                    /*
                    val datas = arrayListOf<DiaryImgData>(
                        DiaryImgData(itemdata1!!.date, arrayListOf(itemdata1!!.postInfos!!.get(0).image, itemdata1!!.postInfos!!.get(1).image))
                        // DiaryImgData(itemdata1!!.date, arrayListOf(itemdata1!!.postInfos!!.get(4).image, itemdata1!!.postInfos!!.get(5).image, itemdata1!!.postInfos!!.get(7).image, itemdata1!!.postInfos!!.get(0).image)),
                        // DiaryImgData(itemdata2!!.date, arrayListOf(itemdata2!!.postInfos!!.get(0).image, itemdata2!!.postInfos!!.get(1).image)),
                        // DiaryImgData(itemdata3!!.date, arrayListOf(itemdata3!!.postInfos!!.get(0).image, itemdata3!!.postInfos!!.get(1).image, itemdata3!!.postInfos!!.get(2).image))
                    )*/
                    binding.rvDiaryImgCalendar.apply {
                        adapter = DiaryImgCalendarAdapter().build(datas)
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_Entire", "여긴가?")
                }
            }

            override fun onFailure(call: Call<List<ResponseEntireDiaryData>>, t: Throwable) {
                Log.d("Network_Entire", "entireDiary get error!")

            }
        })
        return root
    }
}