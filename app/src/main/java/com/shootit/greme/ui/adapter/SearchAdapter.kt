package com.shootit.greme.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shootit.greme.R
import com.shootit.greme.databinding.ItemDiaryimgBinding
import com.shootit.greme.model.SearchData
import com.shootit.greme.ui.view.OtherUserDiaryActivity

class SearchAdapter(var mList: List<SearchData>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var otheruserpostId : Int = 0
    inner class ViewHolder(val binding: ItemDiaryimgBinding) : RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context
        /*
        fun bind(item: String) {
            Glide.with(itemView).load(item).into(binding.ivDiaryImg)
            // postId = item.postId
        }*/
        fun bind(item: SearchData) {
            Glide.with(itemView).load(item.img).into(binding.ivDiaryImg)
            otheruserpostId = item.postId
            itemView.setOnClickListener {
                val intent = Intent(context, OtherUserDiaryActivity::class.java)
                intent.putExtra("otheruserpostId", item);
                intent.run { context.startActivity(this)}
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDiaryimgBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }
    override fun getItemCount(): Int = mList.size
}