package com.shootit.greme.ui.adapter

import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shootit.greme.databinding.ItemDiaryimgBinding
import com.shootit.greme.ui.view.DiaryDetailActivity

class DiaryImgAdapter : RecyclerView.Adapter<DiaryImgAdapter.ViewHolder>() {
    lateinit var items: ArrayList<Int>

    fun build(i: ArrayList<Int>): DiaryImgAdapter{
        items = i
        return this
    }
    inner class ViewHolder(val binding: ItemDiaryimgBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(item: Int){
            Glide.with(itemView).load(item).into(binding.ivDiaryImg)

            itemView.setOnClickListener {
                val intent = Intent(context, DiaryDetailActivity::class.java)
                intent.run { context.startActivity(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDiaryimgBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}