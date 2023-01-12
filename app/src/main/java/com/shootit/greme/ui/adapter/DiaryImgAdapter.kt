package com.shootit.greme.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shootit.greme.databinding.ItemDiaryimgBinding

class DiaryImgAdapter : RecyclerView.Adapter<DiaryImgAdapter.ViewHolder>() {
    lateinit var items: ArrayList<Int>

    fun build(i: ArrayList<Int>): DiaryImgAdapter{
        items = i
        return this
    }
    class ViewHolder(val binding: ItemDiaryimgBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Int){
            Glide.with(itemView).load(item).into(binding.ivDiaryImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDiaryimgBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}