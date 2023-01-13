package com.shootit.greme.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentDiaryImgCalendarBinding
import com.shootit.greme.model.DiaryImgData
import com.shootit.greme.ui.adapter.DiaryImgCalendarAdapter

class DiaryImgCalendarFragment : Fragment() {
    lateinit var binding: FragmentDiaryImgCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        binding = FragmentDiaryImgCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.rvDiaryImgCalendar.apply {
            adapter = DiaryImgCalendarAdapter().build(datas)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        return root
    }
    val datas = arrayListOf<DiaryImgData>(
        DiaryImgData("2022.11", arrayListOf(R.drawable.ex_img, R.drawable.ex_img2, R.drawable.ex_img, R.drawable.ex_img2,R.drawable.ex_img, R.drawable.ex_img2)),
        DiaryImgData("2022.10", arrayListOf(R.drawable.ex_img, R.drawable.ex_img2, R.drawable.ex_img, R.drawable.ex_img2,R.drawable.ex_img, R.drawable.ex_img2)),
        DiaryImgData("2022.09", arrayListOf(R.drawable.ex_img, R.drawable.ex_img2, R.drawable.ex_img, R.drawable.ex_img2,R.drawable.ex_img, R.drawable.ex_img2))
    )
}