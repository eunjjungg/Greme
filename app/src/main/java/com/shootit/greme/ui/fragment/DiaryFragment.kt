package com.shootit.greme.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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
import com.shootit.greme.model.CalendarData
import com.shootit.greme.ui.adapter.CalendarAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import org.w3c.dom.Text
import java.io.File
import java.util.*

class DiaryFragment : Fragment(R.layout.fragment_diary) {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentDiaryBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var imageFile : File? = null

    lateinit var calendarAdapter: CalendarAdapter
    private var calendarList = ArrayList<CalendarData>()

    companion object {
        const val REVIEW_MIN_LENGTH = 10
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
        // API 호출시 Parameter Key 값
        const val PARAM_KEY_IMAGE = "image"
        const val PARAM_KEY_PRODUCT_ID = "product_id"
        const val PARAM_KEY_REVIEW = "review_content"
        const val PARAM_KEY_RATING = "rating"
    }
    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == RESULT_OK){
            // 이미지를 받으면 ImageView에 적용
            val imageUri = result.data?.data
            imageUri?.let{
                // 서버 업로드를 위해 파일 형태로 변환
                imageFile = File(getRealPathFromURI(it))

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
            }
        })
        binding.btnSave.setOnClickListener {
            binding.clToday.visibility = View.VISIBLE
            binding.clDisclosure.visibility = View.GONE
            binding.btnSave.visibility = View.GONE
            binding.clEdit.visibility = View.VISIBLE
        }
        binding.ivCalendar.setOnClickListener {
            val diaryImgCalendarFragment = DiaryImgCalendarFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_fl, diaryImgCalendarFragment)
                .commitNow()
        }
        setupSpinner()
        setupSpinnerHandler()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarAdapter = CalendarAdapter(calendarList)

        calendarList.apply {

            // 오늘 요일 출력
            val forDay = LocalDate.now().dayOfWeek
            var weekday = ""
            if(forDay.toString() == "SUNDAY"){
                weekday = "SUN"
            }
            val now = LocalDate.now().format(ofPattern("d"))
            binding.tvMonth.text = LocalDate.now().month.toString()
            binding.tvYear.text = LocalDate.now().year.toString()

            for (i in 0..6) {
                // Log.d("날짜만", week_day[i])

                calendarList.apply {
                    // 오늘을 기준으로 +-3일 값들 출력
                    add(CalendarData((now.toInt()+i.toLong()-3).toString(),weekday))
                }
            }
        }
        binding.rvCalendar.adapter = calendarAdapter
        binding.rvCalendar.layoutManager = GridLayoutManager(context, 7)

        binding.ivMain.setOnClickListener {
            selectGallery()
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
}