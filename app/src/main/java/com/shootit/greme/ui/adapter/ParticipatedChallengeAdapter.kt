package com.shootit.greme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shootit.greme.R
import com.shootit.greme.model.ChallengeData

class ParticipatedChallengeAdapter(private val context: Context): RecyclerView.Adapter<ParticipatedChallengeAdapter.ViewHolder>() {
    var datas = mutableListOf<ChallengeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ParticipatedChallengeAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_participated_challenge, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ParticipatedChallengeAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtTitle: TextView = itemView.findViewById(R.id.tvChallengeTitle)
        private val txtContent: TextView = itemView.findViewById(R.id.tvChallengeContent)
        private val imgProfile: ImageView = itemView.findViewById(R.id.ivProfile)
        private val txtParticipant: TextView = itemView.findViewById(R.id.tvChallengeParticipant)
        private val txtDay: TextView = itemView.findViewById(R.id.tvDday)

        fun bind(item:ChallengeData){
            txtTitle.text = item.title
            txtContent.text = item.content
            Glide.with(itemView).load(item.img).into(imgProfile)
            txtParticipant.text = item.participant
            txtDay.text = item.day
        }
    }
}