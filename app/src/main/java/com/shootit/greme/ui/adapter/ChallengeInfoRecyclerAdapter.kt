package com.shootit.greme.ui.adapter

import android.R as AR
import android.content.res.Resources
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.StreamEncoder
import com.caverock.androidsvg.SVG
import com.shootit.greme.R
import com.shootit.greme.databinding.LayoutChallengeDefaultGuideBinding
import com.shootit.greme.databinding.LayoutChallengeGridImageBinding
import com.shootit.greme.databinding.LayoutChallengeListBinding
import com.shootit.greme.model.*
import java.io.InputStream


class ChallengeInfoRecyclerAdapter(private val resources: Resources)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imgDataList: MutableList<ChallengeInfoImg> = mutableListOf()
    var challengeInfo: ChallengeInfo = ChallengeInfo("null", "null", 0, false)

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
                val binding = LayoutChallengeGridImageBinding.inflate(layoutInflater, parent, false)
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
                holder.bind(imgDataList[position - TYPE_IMG])
            }
        }
    }

    override fun getItemCount(): Int {
        return imgDataList.size + TYPE_IMG
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class DefaultGuideViewHolder(private val binding: LayoutChallengeDefaultGuideBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tv.text = "챌린지 참여 목록\n"
        }
    }

    private inner class DefaultChallengeInfoViewHolder(private val binding: LayoutChallengeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChallengeInfo) {
            binding.apply {
                ViewCompat.setTransitionName(binding.outerView, "infoView")
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

    private inner class ImageViewHolder(private val binding: LayoutChallengeGridImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challengeInfoImg: ChallengeInfoImg) {
            val outerLayout = binding.outerView
            val outerParams: ViewGroup.LayoutParams = outerLayout.layoutParams
            val innerLayoutList = listOf(binding.imgView1, binding.imgView2, binding.imgView3)
            val innerParamsList = listOf(binding.imgView1.layoutParams, binding.imgView2.layoutParams, binding.imgView3.layoutParams)

            outerLayout.viewTreeObserver.addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        outerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                        val width: Int = outerLayout.measuredWidth
                        outerParams.height = width / 3
                        outerLayout.layoutParams = outerParams
                        innerParamsList.forEachIndexed { index, params ->
                            params.height = width / 3
                            params.width = width / 3
                            innerLayoutList[index].layoutParams = params

                        }

                    }
                })

            Log.d("ccheck", "glide check")
            Glide.with(itemView)
                .load(Uri.parse("https://exchange-data-s3-bucket.s3.ap-northeast-2.amazonaws.com/profile/image.png"))
                //.load(Uri.parse("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F230F184857C8F8D50E"))
                .placeholder(resources.getDrawable(com.shootit.greme.R.drawable.ic_challenge_earth2))
                .error(resources.getDrawable(com.shootit.greme.R.drawable.ic_recycle))
                .override(binding.imgView1.width, binding.imgView1.height)
                .into(binding.imgView1)



            if(challengeInfoImg.secondImgUrl == null){
                binding.imgView2.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    com.shootit.greme.R.drawable.drawable_challenge_summary, null))
            }
            if(challengeInfoImg.thirdImgUrl == null) {
                binding.imgView3.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.drawable_challenge_summary, null))
            }
        }
    }
}