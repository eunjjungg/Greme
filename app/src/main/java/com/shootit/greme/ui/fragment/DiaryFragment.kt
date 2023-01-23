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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shootit.greme.R
import com.shootit.greme.databinding.FragmentDiaryBinding
import com.shootit.greme.ui.adapter.CalendarAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import com.shootit.greme.model.*
import com.shootit.greme.network.ConnectionObject
import com.shootit.greme.util.EncryptedSpfImpl
import com.shootit.greme.util.EncryptedSpfObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class DiaryFragment : Fragment(R.layout.fragment_diary) {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentDiaryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var imageFile : File? = null
    var imageWideUri: Uri? = null
    var postId : Long = 0

    lateinit var calendarAdapter: CalendarAdapter
    private var calendarList = ArrayList<CalendarData>()

    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        // 삭제하기 버튼 서버 연동
        Log.d("TestLog", "Diary")
        // 서버로 보낼 로그인 데이터 생성
        val diaryDeleteData = DiaryDeleteData(postId.toInt())
        Log.d("datavalue", "data값=> "+ diaryDeleteData)
        Log.d("datavalue", "postId Int값=> "+ postId.toInt())
        // 현재 사용자의 정보를 받아올 것을 명시
        // 서버 통신은 I/O 작업이므로 비동기적으로 받아올 Callback 내부 코드는 나중에 데이터를 받아오고 실행
        val call: Call<Void> = ConnectionObject.getDiaryWriteRetrofitService.diaryDelete(diaryDeleteData)
        call.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>,response: Response<Void>) {
                val data = response.code()
                Log.d("status code", data.toString())
                // 네트워크 통신에 성공한 경우
                if(response.isSuccessful){
                    // 다이어리 작성한 것들 초기화하기
                    binding.ivMain.setImageResource(R.drawable.ic_plus)
                    binding.etHashtag.text = null
                    binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext)
                    binding.etContent.text = null
                    binding.etContent.setBackgroundResource(R.drawable.bg_diary_edittext)
                    binding.cbDisclosure.isChecked = false
                    // 저장하기 버튼 다시 생성
                    binding.clToday.visibility = View.GONE
                    binding.clDisclosure.visibility = View.VISIBLE
                    binding.btnSave.visibility = View.VISIBLE
                    binding.clEdit.visibility = View.GONE

                    Log.d("Network_Delete", "success")
                    val data = response.body().toString()
                    Log.d("responsevalue", "response값=> "+ data)
                } else
                {
                    // 이곳은 에러 발생할 경우 실행됨
                    val data = response.code()
                    Log.d("status code", data.toString())
                    val data2 = response.headers()
                    Log.d("header", data2.toString())
                    Log.d("server err", response.errorBody()?.string().toString())
                    Log.d("Network_Delete", "fail")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Network_Delete", "error!")
            }
        })
    }
    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int ->
        toast("취소")
    }

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
                // 서버 업로드를 위해 파일 형태로 변환
                // imageFile = File(getRealPathFromURI(it))

                // 이미지를 불러온다
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(binding.ivMain)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        mBinding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        AndroidThreeTen.init(context)
        // edittext 안에 값이 들어갈 경우 background 변경
        binding.etHashtag.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext_write)
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.etHashtag.setBackgroundResource(R.drawable.bg_diary_edittext_write)
                binding.etHashtag.isCursorVisible = false
            }
        })
        // edittext 안에 값이 들어갈 경우 background 변경
        binding.etContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etContent.setBackgroundResource(R.drawable.bg_diary_edittext)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.etContent.setBackgroundResource(R.drawable.bg_diary_edittext_write)
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.etContent.setBackgroundResource(R.drawable.bg_diary_edittext_write)
                binding.etContent.isCursorVisible = false
            }
        })

        binding.ivCalendar.setOnClickListener {
            val diaryImgCalendarFragment = DiaryImgCalendarFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_fl, diaryImgCalendarFragment)
                .commitNow()
        }

        // 삭제하기 버튼 다이얼로그 창 띄우기
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
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
        setupSpinner()
        setupSpinnerHandler()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var week_day: Array<String> = resources.getStringArray(R.array.calendar_day)
        calendarAdapter = CalendarAdapter(calendarList)

        calendarList.apply {

            /*
            // 오늘 요일 출력
            val forDay = LocalDate.now().dayOfWeek
            var weekday = ""
            if (forDay.toString() == "MONDAY") {
                weekday = "MON"
            }*/
            val now = LocalDate.now().format(ofPattern("d"))
            binding.tvMonth.text = LocalDate.now().month.toString()
            binding.tvYear.text = LocalDate.now().year.toString()

            for (i in 0..6) {
                // Log.d("날짜만", week_day[i])

                calendarList.apply {
                    // 오늘을 기준으로 +-3일 값들 출력
                    add(CalendarData((now.toInt() + i.toLong() - 3).toString(), week_day[i]))
                }
            }
        }
        binding.rvCalendar.adapter = calendarAdapter
        binding.rvCalendar.layoutManager = GridLayoutManager(context, 7)

        binding.ivMain.setOnClickListener {
            selectGallery()
        }
        // DiaryWrite 서버 연결 부분
        binding.btnSave.setOnClickListener {
            Log.d("TestLog", "Diary")
            Log.d("datavalue", "multipart값=> " + imageWideUri)
            imageFile = File(getRealPathFromURI(imageWideUri!!))
            // 서버로 보내기 위해 RequestBody객체로 변환
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
            val body =
                MultipartBody.Part.createFormData("multipartFile", imageFile!!.name, requestFile)

            // 현재 사용자의 정보를 받아올 것을 명시
            // 서버 통신은 I/O 작업이므로 비동기적으로 받아올 Callback 내부 코드는 나중에 데이터를 받아오고 실행
            val call: Call<Long> =
                ConnectionObject.getDiaryWriteRetrofitService.diaryWrite(
                    binding.etContent.text.toString(),
                    binding.etHashtag.text.toString(),
                    1,
                    binding.cbDisclosure.isChecked,
                    body
                )
            call.enqueue(object : Callback<Long> {
                override fun onResponse(
                    call: Call<Long>, response: Response<Long>
                ) {
                    val data = response.code()
                    Log.d("status code", data.toString())
                    // 네트워크 통신에 성공한 경우
                    if (response.isSuccessful) {
                        binding.clToday.visibility = View.VISIBLE
                        binding.clDisclosure.visibility = View.GONE
                        binding.btnSave.visibility = View.GONE
                        binding.clEdit.visibility = View.VISIBLE

                        Log.d("Network_DiaryWrite", "success")
                        val data = response.body()
                        Log.d("responsevalue", "response값=> " + data)
                        postId = data!!
                        Log.d("responsevalue", "postId값=> " + postId)
                    } else { // 이곳은 에러 발생할 경우 실행됨
                        val data1 = response.code()
                        Log.d("status code", data1.toString())
                        val data2 = response.headers()
                        Log.d("header", data2.toString())
                        Log.d("server err", response.errorBody()?.string().toString())
                        Log.d("Network_DiaryWrite", "fail")
                    }
                }

                override fun onFailure(call: Call<Long>, t: Throwable) {
                    Log.d("Network_DiaryWrite", "error!")
                }
            })
        }
    }
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_challenge,R.layout.color_spinner_layout)
        adapter.setDropDownViewResource(R.layout.spinner_style)
        binding.spinnerDiary.adapter = adapter
    }
    private fun setupSpinnerHandler() {
        binding.spinnerDiary.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
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
    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
    fun toast(message:String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}