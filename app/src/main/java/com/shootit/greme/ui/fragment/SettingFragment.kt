package com.shootit.greme.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentSettingBinding
import com.shootit.greme.model.ChallengeData
import com.shootit.greme.model.ResponseDateDiaryData
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.ui.adapter.ParticipatedChallengeAdapter
import com.shootit.greme.ui.view.SettingUserInfoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingFragment : Fragment(R.layout.fragment_setting) {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentSettingBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    lateinit var participatedChallengeAdapter: ParticipatedChallengeAdapter
    val datas = mutableListOf<ChallengeData>()

    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        toast("확인")
    }
    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int ->
        toast("취소")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        mBinding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRecycler()
        binding.btnProfileModify.setOnClickListener {

            // profile setting 화면 이동
            // TODO transition animation 고민중...
            Intent(binding.root.context, SettingUserInfoActivity::class.java).also {
                //val pair: androidx.core.util.Pair<View, String> = androidx.core.util.Pair(binding.btnProfileModify, "pageName")
                //val optionPair = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SettingFragment.activity as Activity, pair)
                //startActivity(it, optionPair.toBundle())
                startActivity(it)
            }

//            val profileeditFragment = ProfileEditFragment()
//            requireActivity().supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.nav_fl, profileeditFragment)
//                .commitNow()
        }
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("로그아웃")
                .setMessage("로그아웃하시겠습니까?")
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
        binding.btnSecession.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("정말 탈퇴하시겠습니까?")
                .setMessage("서비스에 등록된 모든 데이터가 삭제됩니다.")
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
            button1.setOnClickListener {
                Log.d("Network_SignOut", "signout")

                ConnectionObject.getSignOutRetrofitService.signOut(0).enqueue(object :
                    Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful){
                            Log.d("Network_DiaryWrite", "success")
                            val data = response.body().toString()
                            Log.d("responsevalue", "signout_response 값 => "+ data)
                            val data1 = response.code()
                            Log.d("status code", data1.toString())
                        }else{
                            // 이곳은 에러 발생할 경우 실행됨
                            val data1 = response.code()
                            Log.d("status code", data1.toString())
                            val data2 = response.headers()
                            Log.d("header", data2.toString())
                            Log.d("server err", response.errorBody()?.string().toString())
                            Log.d("Network_SignOut", "fail")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("Network_SignOut", "error!")

                    }
                })
            }
        }
        return root
    }
    private fun initRecycler(){
        participatedChallengeAdapter = ParticipatedChallengeAdapter(requireContext())
        binding.rvChallenge.adapter = participatedChallengeAdapter

        datas.apply{
            add(ChallengeData(title = "가까운_거리는_걸어다니기", content = "도보로 15분 이내의 거리는 걸어다니기", img = R.drawable.ic_profile, participant = "9", day = "D-8"))
            add(ChallengeData(title = "텀블러_이용하기", content = "카페가서 텀블러 이용하기", img = R.drawable.ic_profile, participant = "25", day = "D-10"))
            add(ChallengeData(title = "따뜻하게_입고_다니기", content = "난방 대신 따뜻한 옷을 입어요", img = R.drawable.ic_profile, participant = "12", day = "D-17"))
            participatedChallengeAdapter.datas=datas
            participatedChallengeAdapter.notifyDataSetChanged()
        }
    }
    fun toast(message:String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}