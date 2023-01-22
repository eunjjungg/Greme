package com.shootit.greme.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentSettingBinding
import com.shootit.greme.model.*
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.ui.adapter.DiaryImgCalendarAdapter
import com.shootit.greme.ui.adapter.ParticipatedChallengeAdapter
import com.shootit.greme.ui.view.SettingUserInfoActivity
import com.shootit.greme.util.RemainingDateCalculator.serverTimeToDDay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SettingFragment : Fragment(R.layout.fragment_setting) {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentSettingBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var imageFile : File? = null
    var imageWideUri: Uri? = null

    lateinit var participatedChallengeAdapter: ParticipatedChallengeAdapter
    val datas = mutableListOf<ChallengeData>()

    companion object {
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }
    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == RESULT_OK){
            // 이미지를 받으면 ImageView에 적용
            val imageUri = result.data?.data
            imageWideUri = imageUri
            imageUri?.let{
                // 이미지를 불러온다
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(binding.ivProfile)

                // 프로필 사진 변경 서버 연동
                Log.d("TestLog", "ProfileEdit")
                // Log.d("datavalue", "multipart값=> " + imageWideUri)
                imageFile = File(getRealPathFromURI(it))
                // 서버로 보내기 위해 RequestBody객체로 변환
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
                val body =
                    MultipartBody.Part.createFormData("multipartFile", imageFile!!.name, requestFile)

                // 현재 사용자의 정보를 받아올 것을 명시
                // 서버 통신은 I/O 작업이므로 비동기적으로 받아올 Callback 내부 코드는 나중에 데이터를 받아오고 실행
                val call: Call<Void> =
                    ConnectionObject.getSettingRetrofitService.setProfileImage(body)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>, response: Response<Void>
                    ) {
                        val data = response.code()
                        Log.d("status code", data.toString())
                        // 네트워크 통신에 성공한 경우
                        if (response.isSuccessful) {
                            Log.d("Network_ProfileEdit", "success")
                            val data = response.body()
                            Log.d("responsevalue", "response값=> " + data)
                        } else { // 이곳은 에러 발생할 경우 실행됨
                            val data1 = response.code()
                            Log.d("status code", data1.toString())
                            val data2 = response.headers()
                            Log.d("header", data2.toString())
                            Log.d("server err", response.errorBody()?.string().toString())
                            Log.d("Network_ProfileEdit", "fail")
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("Network_ProfileEdit", "error!")
                    }
                })
            }
        }
    }

    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        Log.d("Network_SignOut", "signout")
        ConnectionObject.getSignOutRetrofitService.signOut().enqueue(object :
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
        binding.btnProfileModify.setOnClickListener {

            // profile setting 화면 이동
            // TODO transition animation 고민중...
            Intent(binding.root.context, SettingUserInfoActivity::class.java).also {
                //val pair: androidx.core.util.Pair<View, String> = androidx.core.util.Pair(binding.btnProfileModify, "pageName")
                //val optionPair = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SettingFragment.activity as Activity, pair)
                //startActivity(it, optionPair.toBundle())
                startActivity(it)
            }
        }
        // setting 첫 화면 서버 연동
        Log.d("Network_setting", "settingInfo")
        ConnectionObject.getSettingRetrofitService.getSettigInfo().enqueue(object :
            Callback<ResponseSettingInfoData> {
            override fun onResponse(
                call: Call<ResponseSettingInfoData>,
                response: Response<ResponseSettingInfoData>
            ) {
                if (response.isSuccessful){
                    Log.d("Network_setting", "success")
                    val itemdata1 = response.body()
                    Log.d("Network_setting", "response=>" + itemdata1)
                    binding.tvId.text = itemdata1!!.username
                    Glide.with(this@SettingFragment).load(itemdata1!!.imageUrl).into(binding.ivProfile)
                    participatedChallengeAdapter = ParticipatedChallengeAdapter(requireContext())
                    binding.rvChallenge.adapter = participatedChallengeAdapter

                    datas.apply{
                        add(ChallengeData(title = itemdata1!!.challengeJoinSummary.get(0).title, content = itemdata1!!.challengeJoinSummary.get(0).info, img = R.drawable.ic_profile, participant = itemdata1!!.challengeJoinSummary.get(0).num.toString(), day = "D-" + itemdata1!!.challengeJoinSummary.get(0).deadline.serverTimeToDDay().toString()))
                        participatedChallengeAdapter.datas=datas
                        participatedChallengeAdapter.notifyDataSetChanged()
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_setting", "fail")
                }
            }
            override fun onFailure(call: Call<ResponseSettingInfoData>, t: Throwable) {
                Log.d("Network_setting", "error!")
            }
        })
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
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnChangeProfile.setOnClickListener {
            selectGallery()
        }
    }
    // 이미지 실제 경로 반환
    fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, proj, null,null,null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }
    // 갤러리를 부르는 메서드
    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            // 권한 요청
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY)
        }else{
            // 권한이 있는 경우 갤러리 실행
            val intent = Intent(Intent.ACTION_PICK)
            // intent의 data와 type을 동시에 설정하는 메서드
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*"
            )
            imageResult.launch(intent)
        }
    }
    fun toast(message:String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}