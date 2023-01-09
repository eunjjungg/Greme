package com.shootit.greme.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentDiaryBinding
import com.shootit.greme.model.CalendarData
import com.shootit.greme.ui.adapter.CalendarAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import org.w3c.dom.Text
import java.util.*

class DiaryFragment : Fragment(R.layout.fragment_diary) {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentDiaryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var calendarAdapter: CalendarAdapter
    private var calendarList = ArrayList<CalendarData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        mBinding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        AndroidThreeTen.init(context)
        // edittext 안에 값이 들어갈 경우 background 변경
        binding.etHashtag.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext_write)
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext_write)
            }
        })
        binding.btnSave.setOnClickListener {
            binding.clToday.visibility = View.VISIBLE
            binding.clDisclosure.visibility = View.GONE
            binding.btnSave.visibility = View.GONE
            binding.clEdit.visibility = View.VISIBLE
        }
        setupSpinner()
        setupSpinnerHandler()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // var week_day: Array<String> = resources.getStringArray(R.array.calendar_day)

        calendarAdapter = CalendarAdapter(calendarList)

        calendarList.apply {

            // 오늘 요일 출력
            val forDay = LocalDate.now().dayOfWeek
            var weekday = ""
            if(forDay.toString() == "SUNDAY"){
                weekday = "SUN"
            }
            val now = LocalDate.now().format(ofPattern("d"))
            binding.tvMonth.text = LocalDate.now().month.toString()
            binding.tvYear.text = LocalDate.now().year.toString()

            for (i in 0..6) {
                // Log.d("날짜만", week_day[i])

                calendarList.apply {
                    // 오늘을 기준으로 +-3일 값들 출력
                    add(CalendarData((now.toInt()+i.toLong()-3).toString(),weekday))
                }
            }
        }
        binding.rvCalendar.adapter = calendarAdapter
        binding.rvCalendar.layoutManager = GridLayoutManager(context, 7)
    }
    private fun setupSpinner() {
        val challenge = resources.getStringArray(R.array.spinner_challenge)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, challenge)
        adapter.setDropDownViewResource(R.layout.spinner_style)
        binding.spinnerDiary.adapter = adapter
    }
    private fun setupSpinnerHandler() {
        binding.spinnerDiary.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}