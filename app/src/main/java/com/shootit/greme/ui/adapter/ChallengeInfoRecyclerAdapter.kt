package com.shootit.greme.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.shootit.greme.R
import com.shootit.greme.databinding.ActivityChallengeInfoBinding
import com.shootit.greme.databinding.LayoutChallengeDefaultGuideBinding
import com.shootit.greme.databinding.LayoutChallengeListBinding
import com.shootit.greme.model.*

class ChallengeInfoRecyclerAdapter(private val resources: Resources)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imgDataList: MutableList<ChallengeInfoImg> = mutableListOf()
    var challengeInfo: ChallengeInfo = ChallengeInfo("null", "null", 0, false)
    private val challengeInfoGuide = ChallengeInfoGuide()

    private val TYPE_INFO = 0
    private val TYPE_GUIDE = 1
    private val TYPE_IMG = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_INFO -> {
                val binding = LayoutChallengeListBinding.inflate(layoutInflater, parent, false)
                DefaultChallengeInfoViewHolder(binding)
            }
            TYPE_GUIDE -> {
                val binding = LayoutChallengeDefaultGuideBinding.inflate(layoutInflater, parent, false)
                DefaultGuideViewHolder(binding)
            }
            else -> {
                val binding = LayoutChallengeListBinding.inflate(layoutInflater, parent, false)
                ImageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DefaultGuideViewHolder -> {
                holder.bind()
            }
            is DefaultChallengeInfoViewHolder -> {
                holder.bind(challengeInfo)
            }
            is ImageViewHolder -> {
                holder.bind(imgDataList[position - 2])
            }
        }
    }

    override fun getItemCount(): Int {
        return imgDataList.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class DefaultGuideViewHolder(private val binding: LayoutChallengeDefaultGuideBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tv.text = "챌린지 참여 목록"
        }
    }

    private inner class DefaultChallengeInfoViewHolder(private val binding: LayoutChallengeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChallengeInfo) {
            binding.apply {
                if(item.isRegistered){
                    outerView.background = ResourcesCompat.getDrawable(resources, R.drawable.drawble_challenge_list_ongoing, null)
                }
                viewPeople.visibility = View.GONE
                tvDesc.text = item.desc
                tvTitle.text = item.title
                tvDay.text = "D - ${item.day}"
            }
        }
    }

    private inner class ImageViewHolder(private val binding: LayoutChallengeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challengeInfoImg: ChallengeInfoImg) {

        }
    }
}