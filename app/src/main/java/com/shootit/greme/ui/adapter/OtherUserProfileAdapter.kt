package com.shootit.greme.ui.adapter

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shootit.greme.R
import com.shootit.greme.databinding.LayoutChallengeDefaultGuideBinding
import com.shootit.greme.databinding.LayoutChallengeGridImageBinding
import com.shootit.greme.databinding.LayoutChallengeListBinding
import com.shootit.greme.databinding.LayoutProfileImageIdBinding
import com.shootit.greme.model.ChallengeInfoImg
import com.shootit.greme.model.ChallengeInfoParcelData
import com.shootit.greme.model.Guide
import com.shootit.greme.model.OnGoingChallenge
import com.shootit.greme.ui.view.ChallengeInfoActivity

class OtherUserProfileAdapter(private val resources: Resources) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userImageString: String = ""
    var userName: String = ""
    var challengeList = mutableListOf<OnGoingChallenge>()
    var imgDataList: MutableList<ChallengeInfoImg> = mutableListOf()

    enum class OtherUserProfileRecyclerType(val index: Int) {
        Profile(0),
        Guide(1),
        Challenge(2),
        Image(3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            OtherUserProfileRecyclerType.Profile.index -> {
                val binding =
                    LayoutProfileImageIdBinding.inflate(layoutInflater, parent, false)
                ProfileViewHolder(binding)
            }
            OtherUserProfileRecyclerType.Guide.index -> {
                val binding =
                    LayoutChallengeDefaultGuideBinding.inflate(layoutInflater, parent, false)
                GuideViewHolder(binding)
            }
            OtherUserProfileRecyclerType.Challenge.index -> {
                val binding = LayoutChallengeListBinding.inflate(layoutInflater, parent, false)
                ChallengeViewHolder(binding)
            }
            else -> {
                val binding = LayoutChallengeGridImageBinding.inflate(layoutInflater, parent, false)
                ImageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> {
                holder.bind(userName, userImageString)
            }
            is GuideViewHolder -> {
                holder.bind(Guide("이번달 참여한 챌린지"))
            }
            is ChallengeViewHolder -> {
                holder.bind(challengeList[position - 2])
            }
            is ImageViewHolder -> {
                // 3 : profile + guide top + guide bottom
                holder.bind(imgDataList[position - 3 - challengeList.size])
            }
        }
    }

    override fun getItemCount(): Int {
        return challengeList.size + 2 + imgDataList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            OtherUserProfileRecyclerType.Profile.index -> OtherUserProfileRecyclerType.Profile.index
            OtherUserProfileRecyclerType.Guide.index -> OtherUserProfileRecyclerType.Guide.index
            in 2 until challengeList.size + 2 -> OtherUserProfileRecyclerType.Challenge.index
            challengeList.size + 2 -> OtherUserProfileRecyclerType.Guide.index
            else -> OtherUserProfileRecyclerType.Image.index
        }
    }

    private inner class ProfileViewHolder(private val binding: LayoutProfileImageIdBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userName: String, userProfileImageString: String) {
            if (userProfileImageString != "") {
                setGlideImageInIV(userProfileImageString, binding.ivProfile)
            }
            binding.tvId.text = userName
        }

        private fun setGlideImageInIV(urlString: String, imageView: ImageView) {
            Glide.with(itemView)
                .load(Uri.parse(urlString))
                .error(resources.getDrawable(com.shootit.greme.R.drawable.drawble_challenge_list_ongoing))
                .centerCrop()
                .override(imageView.width, imageView.height)
                .into(imageView)
        }
    }

    private inner class GuideViewHolder(private val binding: LayoutChallengeDefaultGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Guide) {
            if (adapterPosition == 1){
                binding.tv.text = item.text
            } else {
                binding.tv.text = ""
                binding.tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 1f)
            }
        }
    }

    private inner class ChallengeViewHolder(private val binding: LayoutChallengeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnGoingChallenge) {
            binding.outerView.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.drawble_challenge_list_ongoing,
                null
            )
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.desc
                tvDay.text = "D - ${item.day}"
                tvPeopleAmount.text = item.peopleAmount.toString()
                outerView.setOnClickListener {
                    Intent(binding.root.context, ChallengeInfoActivity::class.java).also {
                        val pair: androidx.core.util.Pair<View, String> =
                            androidx.core.util.Pair(binding.outerView as View, "infoView")
                        val optionPair = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            binding.root.context as Activity,
                            pair
                        )
                        it.putExtra(
                            "ChallengeInfo", ChallengeInfoParcelData(
                                item.title, item.desc, item.day, item.peopleAmount, true, item.id
                            )
                        )
                        binding.root.context.startActivity(it, optionPair.toBundle())
                    }
                }
            }
        }
    }

    private inner class ImageViewHolder(private val binding: LayoutChallengeGridImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challengeInfoImg: ChallengeInfoImg) {
            val outerLayout = binding.outerView
            val outerParams: ViewGroup.LayoutParams = outerLayout.layoutParams
            val innerLayoutList = listOf(binding.imgView1, binding.imgView2, binding.imgView3)
            val innerParamsList = listOf(binding.imgView1.layoutParams, binding.imgView2.layoutParams, binding.imgView3.layoutParams)

            // setting size with square
            outerLayout.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
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

            // set image in each imageView
            val item = listOf<String?>(
                challengeInfoImg.firstImgUrl.urlString,
                challengeInfoImg.secondImgUrl?.urlString,
                challengeInfoImg.thirdImgUrl?.urlString)

            item.forEachIndexed { index, urlString ->
                val imageView = innerLayoutList[index]
                if (urlString == null) {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources,
                        com.shootit.greme.R.drawable.drawable_challenge_summary, null))
                } else {
                    setGlideImageInIV(urlString, imageView)
                }
            }

        }

        fun setGlideImageInIV(urlString: String, imageView: ImageView) {
            Glide.with(itemView)
                .load(Uri.parse(urlString))
                .error(resources.getDrawable(com.shootit.greme.R.drawable.drawble_challenge_list_ongoing))
                .centerCrop()
                .override(imageView.width, imageView.height)
                .into(imageView)
        }
    }
}