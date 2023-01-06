package com.shootit.greme.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentSettingBinding
import com.shootit.greme.model.ChallengeData
import com.shootit.greme.ui.adapter.ParticipatedChallengeAdapter

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
            val profileeditFragment = ProfileEditFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_fl, profileeditFragment)
                .commitNow()
        }
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext(),R.style.AppTheme_AlertDialogTheme)
            builder.setTitle("로그아웃")
                .setMessage("로그아웃하시겠습니까?")
                .setPositiveButton("확인",positiveButtonClick)
                .setNegativeButton("취소", negativeButtonClick)

            builder.show()
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