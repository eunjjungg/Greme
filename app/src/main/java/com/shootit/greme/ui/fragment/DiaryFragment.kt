package com.shootit.greme.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentDiaryBinding
import com.shootit.greme.model.CalendarData
import com.shootit.greme.ui.adapter.CalendarAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.TemporalAdjusters.lastDayOfMonth
import org.threeten.bp.temporal.TemporalAdjusters.previous
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
            if(forDay.toString() == "SATURDAY"){
                weekday = "SAT"
            }
            val now = LocalDate.now().format(ofPattern("d"))
            binding.tvMonth.text = LocalDate.now().month.toString()
            binding.tvYear.text = LocalDate.now().year.toString()

            for (i in 0..6) {
                // Log.d("날짜만", week_day[i])

                calendarList.apply {
                    // 오늘을 기준으로 +-3일 값들 출력
                    // todo : week_day 값만 변경하자!!!
                    add(CalendarData((now.toInt()+i.toLong()-3).toString(),weekday))
                }
                // Log.d("저번 주 일요일 기준으로 시작!", preSunday.plusDays(i.toLong()).format(dateFormat))
            }
        }
        binding.rvCalendar.adapter = calendarAdapter
        binding.rvCalendar.layoutManager = GridLayoutManager(context, 7)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}