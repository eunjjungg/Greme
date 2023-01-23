package com.shootit.greme.ui.adapter

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shootit.greme.R
import com.shootit.greme.databinding.ItemDiaryimgBinding
import com.shootit.greme.model.SearchData
import com.shootit.greme.ui.view.OtherUserDiaryActivity

class SearchAdapter(var mList: List<SearchData>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDiaryimgBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        fun bind(item: String) {
            Glide.with(itemView).load(item).into(binding.ivDiaryImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDiaryimgBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position].img)
        // 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
    // 리스너 인터페이스
    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }
    // 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }
    // setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int = mList.size
}