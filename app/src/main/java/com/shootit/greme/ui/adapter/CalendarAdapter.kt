package com.shootit.greme.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.shootit.greme.R
import com.shootit.greme.databinding.ItemCalendarBinding
import com.shootit.greme.model.CalendarData
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern

class CalendarAdapter(private val dataSet: List<CalendarData>): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarData) {
            binding.tvDate.text = item.date
            binding.tvDay.text = item.day

            val today = binding.tvDate.text.toString()
            Log.d("헙2", today)
            // 오늘 날짜
            val now = LocalDate.now().format(ofPattern("d"))
            Log.d("헙", now)
            // 오늘 날짜와 캘린더의 오늘 날짜가 같은 경우 색깔 변경
            if (today == now) {
                binding.tvDate.setTextColor(Color.WHITE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}