package com.shootit.greme.ui.adapter

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.shootit.greme.R
import com.shootit.greme.databinding.LayoutChallengeDefaultGuideBinding
import com.shootit.greme.databinding.LayoutChallengeDefaultTopBinding
import com.shootit.greme.databinding.LayoutChallengeListBinding
import com.shootit.greme.model.*

class ChallengeActivityAdapter(private val resources: Resources)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: MutableList<ChallengeRecyclerType> = mutableListOf<ChallengeRecyclerType>()
    private val TYPE_ONGOING = 0
    private val TYPE_AVAILABLE = 1
    private val TYPE_GUIDE = 2
    private val TYPE_TOPBAR = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_GUIDE -> {
                val binding =
                    LayoutChallengeDefaultGuideBinding.inflate(layoutInflater, parent, false)
                DefaultGuideViewHolder(binding)
            }
            TYPE_TOPBAR -> {
                val binding =
                    LayoutChallengeDefaultTopBinding.inflate(layoutInflater, parent, false)
                DefaultTopViewHolder(binding)
            }
            TYPE_ONGOING -> {
                val binding =
                    LayoutChallengeListBinding.inflate(layoutInflater, parent, false)
                OnGoingViewHolder(binding)
            }
            // Available().index
            else -> {
                val binding =
                    LayoutChallengeListBinding.inflate(layoutInflater, parent, false)
                AvailableViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DefaultGuideViewHolder -> {
                holder.bind(item = dataList[position] as Guide)
            }
            is DefaultTopViewHolder -> {
                holder.bind(item = dataList[position] as TopBar)
            }
            is OnGoingViewHolder -> {
                holder.bind(item = dataList[position] as OnGoingChallenge)
            }
            is AvailableViewHolder -> {
                holder.bind(item = dataList[position] as AvailableChallenge)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is OnGoingChallenge -> {
                TYPE_ONGOING
            }
            is Guide -> {
                TYPE_GUIDE
            }
            is AvailableChallenge -> {
                TYPE_AVAILABLE
            }
            is TopBar -> {
                TYPE_TOPBAR
            }
        }


    }

    inner class DefaultTopViewHolder(private val binding: LayoutChallengeDefaultTopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopBar) {
            Log.d("ccheck", item.successCount.toString())
        }
    }

    inner class DefaultGuideViewHolder(private val binding: LayoutChallengeDefaultGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Guide) {
            binding.tv.text = item.text
        }
    }

    inner class OnGoingViewHolder(private val binding: LayoutChallengeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnGoingChallenge) {
            binding.outerView.background = ResourcesCompat.getDrawable(resources, R.drawable.drawble_challenge_list_ongoing, null)
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.desc
                tvDay.text = "D - ${item.day}"
                tvPeopleAmount.text = item.peopleAmount.toString()
            }
        }
    }

    inner class AvailableViewHolder(private val binding: LayoutChallengeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AvailableChallenge) {
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.desc
                tvDay.text = "D - ${item.day}"
                tvPeopleAmount.text = item.peopleAmount.toString()
            }

        }
    }
}

