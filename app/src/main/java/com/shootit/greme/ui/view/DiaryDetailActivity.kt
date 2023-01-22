package com.shootit.greme.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shootit.greme.databinding.ActivityDiaryDetailBinding
import com.shootit.greme.model.DiaryImgData
import com.shootit.greme.model.ResponseDateDiaryData
import com.shootit.greme.network.ConnectionObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryDetailActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityDiaryDetailBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    lateinit var datas : DiaryImgData
    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        finish()
    }
    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int ->
        toast("취소")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityDiaryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("정말 삭제하시겠습니까?")
                .setMessage("해당 다이어리와 게시물이 모두 삭제됩니다.")
                .setPositiveButton("확인",positiveButtonClick)
                .setNegativeButton("취소", negativeButtonClick)

            val alertDialog = builder.create()
            alertDialog.show()
            val button1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            with(button1){
                setTextColor(Color.RED)
            }
            val button2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            with(button2){
                setTextColor(Color.BLUE)
            }
        }
        // 날짜로 다이어리 조회 서버 연동
        Log.d("Network_Date", "dateDiary")

        ConnectionObject.getDiaryWriteRetrofitService.dateDiaryLook("2023-01-21").enqueue(object :
            Callback<ResponseDateDiaryData> {
            override fun onResponse(
                call: Call<ResponseDateDiaryData>,
                response: Response<ResponseDateDiaryData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    Log.d("responsevalue", "dateDiary_response 값 => "+ data)
                    Glide.with(this@DiaryDetailActivity).load(data!!.image).into(binding.ivMain)
                    binding.tvHashtag.text = data!!.hashtag
                    binding.tvContent.text = data!!.content
                    binding.tvDate.text = "2023/01/21"

                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    val data1 = response.code()
                    Log.d("status code", data1.toString())
                    val data2 = response.headers()
                    Log.d("header", data2.toString())
                    Log.d("server err", response.errorBody()?.string().toString())
                    Log.d("Network_Date", "fail")
                }
            }

            override fun onFailure(call: Call<ResponseDateDiaryData>, t: Throwable) {
                Log.d("Network_Date", "error!")

            }
        })

        // datas = intent.getSerializableExtra("data") as DiaryImgData

        // Glide.with(this).load(datas.img).into(binding.ivMain)
        // binding.tvDate.text = datas.date
    }
    fun toast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}