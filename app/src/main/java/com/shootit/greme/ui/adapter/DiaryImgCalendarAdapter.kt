package com.shootit.greme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shootit.greme.databinding.ItemDiaryimgCalendarBinding
import com.shootit.greme.model.DiaryImgData

class DiaryImgCalendarAdapter : RecyclerView.Adapter<DiaryImgCalendarAdapter.ViewHolder>() {
    lateinit var items : ArrayList<DiaryImgData>

    fun build(i: ArrayList<DiaryImgData>): DiaryImgCalendarAdapter{
        items = i
        return this
    }
    class ViewHolder(val binding: ItemDiaryimgCalendarBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DiaryImgData) {
            with(binding){
                tvDiaryImgMonth.text = item.date
                rvDiaryImg.apply {
                    adapter = DiaryImgAdapter().build(item.img)
                    layoutManager = GridLayoutManager(context, 3)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryImgCalendarAdapter.ViewHolder =
        ViewHolder(ItemDiaryimgCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)

    override fun onBindViewHolder(holder: DiaryImgCalendarAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}