package com.shootit.greme.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.shootit.greme.databinding.ActivityOtherUserDiaryBinding
import com.shootit.greme.model.ResponseOtherUserDiaryData
import com.shootit.greme.model.SearchData
import com.shootit.greme.network.ConnectionObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherUserDiaryActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityOtherUserDiaryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var userId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityOtherUserDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            finish()
        }

        // 다른 유저의 다이어리 조회 서버 연동
        val otherUserPostId = intent.getParcelableExtra<SearchData>("otheruserpostId")
        Log.d("SearchValue", "Search_postId 값 => " + otherUserPostId)
        ConnectionObject.getDiarySearchRetrofitService.otherUserDiary(otherUserPostId!!.postId)
            .enqueue(object : Callback<ResponseOtherUserDiaryData> {
                override fun onResponse(
                    call: Call<ResponseOtherUserDiaryData>,
                    response: Response<ResponseOtherUserDiaryData>
                ) {
                    val data = response.code()
                    Log.d("status code", data.toString())
                    if (response.isSuccessful) {
                        Log.d("Network_OtherUser", "success")
                        val data = response.body().toString()
                        Log.d("responsevalue", "otherUserDiary_response 값 => " + data)
                        val itemdata1 = response.body()
                        var date = itemdata1!!.createdDate.substring(0 until 10)
                        date = date.replace("-", "/")
                        userId = itemdata1!!.userId
                        binding.tvOtherUserID.text = itemdata1!!.username
                        Glide.with(this@OtherUserDiaryActivity).load(itemdata1!!.image).into(binding.ivMain)
                        binding.tvHashtag.text = itemdata1!!.hashtag
                        binding.tvContent.text = itemdata1!!.content
                        binding.tvDate.text = date
                    } else {
                        // 이곳은 에러 발생할 경우 실행됨
                        val data1 = response.code()
                        Log.d("status code", data1.toString())
                        val data2 = response.headers()
                        Log.d("header", data2.toString())
                        Log.d("server err", response.errorBody()?.string().toString())
                        Log.d("Network_OtherUser", "fail")
                    }
                }
                override fun onFailure(call: Call<ResponseOtherUserDiaryData>, t: Throwable) {
                    Log.d("Network_OtherUser", "error!")

                }
            })

        binding.tvOtherUserID.setOnClickListener {
            Intent(binding.root.context, OtherUserProfileActivity::class.java).also {
                it.putExtra("userId", userId)
                startActivity(it)
            }
        }
    }
}