package com.example.gukrule.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.gukrule.LoginActivity
import com.example.gukrule.R
import com.example.gukrule.SignUpActivity
import com.example.gukrule.databinding.FragmentSignUpInfoBinding
import com.example.gukrule.retrofit.RegisterData
import com.example.gukrule.retrofit.RegisterResponse
import com.example.gukrule.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class SignUpInfoFragment : Fragment() {

    private lateinit var signUpActivity: SignUpActivity
    private var _binding: FragmentSignUpInfoBinding? = null
    private val binding get() = _binding!!

    // state boolean
    private var isIdOverlapping = false // 아이디 중복 여부
    private var isNameAcceptable = false // 이름 예외처리 여부
    private var isIdAcceptable = false // 아이디 예외처리 여부
    private var isPwAcceptable = false // 비밀번호 예외처리 여부
    private var isRePwAcceptable = false // 비밀번호 재입력 예외처리 여부
    private var isEmailAcceptable = false // 이메일 예외처리 여부
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = activity as SignUpActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        val editTextList = arrayListOf(binding.nameET, binding.idET, binding.pwET, binding.rePwET, binding.emailET)

        // domain Spinner
        val domainList = resources.getStringArray(R.array.domain_array)

        // 도메인 스피너 적용
        binding.domainSpinner.adapter = ArrayAdapter(signUpActivity, android.R.layout.simple_spinner_item, domainList)
        binding.domainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var doamain = binding.domainSpinner.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        // 예외처리
        for (i in 0 until editTextList.size) {
            editTextList[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) { }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when (i) {
                        0 -> nameExceptionHandling() // 이름 예외처리 함수 호출부
                        1 -> idExceptionHandling() // 아이디 예외처리 함수 호출부
                        2 -> pwExceptionHandling() // 비밀번호 예외처리 함수 호출부
                        3 -> rePwExceptionHandling() // 비밀번호 재입력 예외처리 함수 호출부
                        4 -> emailExceptionHandling() // 이메일 예외처리 함수 호출부
                    }
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit })
        }

        binding.idOverlapCheckBtn.setOnClickListener {
            overlapCheckEvent()
        }

        // 다음 페이지 이동 (회원가입 완료)
        binding.nextPageBtn.setOnClickListener {
            moveNextPageEvent()
        }

        // 이전 페이지로 이동
        binding.backPageBtn.setOnClickListener {
            moveBackPageEvent()
        }

        return root
    }

    // 이름 예외처리
    private fun nameExceptionHandling() {
        isNameAcceptable = false

        if(binding.nameET.length() < 2) {
            binding.nameInputLayout.error = "이름을 입력해주세요."
        } else {
            binding.nameInputLayout.error = null
            binding.nameET.backgroundTintList = ContextCompat.getColorStateList(signUpActivity ,R.color.blue)
            binding.nameInputLayout.defaultHintTextColor = ContextCompat.getColorStateList(signUpActivity,R.color.blue)
            isNameAcceptable = true
        }
    }

    // 아이디 예외처리
    private fun idExceptionHandling() {
        isIdAcceptable = false
        isIdOverlapping = false

        if (binding.idET.length() < 4) {
            binding.idInputLayout.error = "4자 이상 입력해주세요."
            binding.idOverlapCheckBtn.isEnabled = false
        }
        else if (!Pattern.matches("^[A-Za-z0-9]*$",binding.idET.text)) {
            binding.idInputLayout.error = "아이디 형식에 부합하지 않습니다."
            binding.idOverlapCheckBtn.isEnabled = false
        }
        else {
            binding.idInputLayout.error = null
            binding.idET.backgroundTintList = ContextCompat.getColorStateList(signUpActivity, R.color.blue)
            binding.idInputLayout.defaultHintTextColor = ContextCompat.getColorStateList(signUpActivity, R.color.blue)
            binding.idOverlapCheckBtn.isEnabled = true
            isIdAcceptable = true
        }
    }

    // 비밀번호 예외처리
    private fun pwExceptionHandling() {
        isPwAcceptable = false

        if (binding.pwET.length() < 8) {
            binding.pwInputLayout.error = "8자 이상 입력해주세요."
        } else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{8,20}$", binding.pwET.text)) {
            binding.pwInputLayout.error = "비밀번호 형식이 올바르지 않습니다."
        } else {
            binding.pwInputLayout.error = null
            binding.pwET.backgroundTintList = ContextCompat.getColorStateList(signUpActivity, R.color.blue)
            binding.pwInputLayout.defaultHintTextColor = ContextCompat.getColorStateList(signUpActivity, R.color.blue)
            isPwAcceptable = true
        }
    }

    // 비밀번호 재입력 예외처리
    private fun rePwExceptionHandling() {
        isRePwAcceptable = false

        Log.d("TAG", "pw : ${binding.pwET.text} rePw : ${binding.rePwET.text}")
        if (binding.pwET.text.toString() != binding.rePwET.text.toString()) {
            binding.rePwInputLayout.error = "비밀번호가 일치하지 않습니다."
        } else {
            binding.rePwInputLayout.error = null
            binding.rePwET.backgroundTintList = ContextCompat.getColorStateList(signUpActivity, R.color.blue)
            binding.rePwInputLayout.defaultHintTextColor = ContextCompat.getColorStateList(signUpActivity, R.color.blue)
            isRePwAcceptable = true
        }
    }

    // 이메일 예외처리
    private fun emailExceptionHandling() {
        isEmailAcceptable = false

        if (binding.emailET.length() < 1) {
            binding.emailInputLayout.error = "이메일을 입력해주세요."
        }  else {
            binding.emailInputLayout.error = null
            binding.emailET.backgroundTintList = ContextCompat.getColorStateList(signUpActivity, R.color.gray)
            binding.emailInputLayout.defaultHintTextColor = ContextCompat.getColorStateList(signUpActivity, R.color.gray)
            isEmailAcceptable = true
        }
    }

    private fun overlapCheckEvent() {
        AlertDialog.Builder(signUpActivity)
            .setMessage("사용가능한 아이디입니다.")
            .setPositiveButton("확인", null)
            .show()

        isIdOverlapping = true
    }

    private fun moveBackPageEvent() {
        signUpActivity.transFragEvent(0)
    }

    private fun moveNextPageEvent() {
        signUpActivity.transFragEvent(2)
//        if (!isNameAcceptable) signUpActivity.showToastEvent("이름을 확인해주세요.", true)
//        else if (!isIdAcceptable) signUpActivity.showToastEvent("아이디를 확인해주세요.", true)
//        else if (!isIdOverlapping) signUpActivity.showToastEvent("아이디가 중복 확인되지 않았습니다.", true)
//        else if (!isPwAcceptable) signUpActivity.showToastEvent("비밀번호를 확인해주세요.", true)
//        else if (!isRePwAcceptable) signUpActivity.showToastEvent("비밀번호가 서로 일치하지 않습니다.", true)
//        else if (!isEmailAcceptable) signUpActivity.showToastEvent("이메일을 확인해주세요.", true)
//        else {
//            connectRegisterApi()
//            signUpActivity.transFragEvent(2)
//        }
    }

    //TODO::register Data class를 만들고 API 통신 test
    private fun connectRegisterApi() {
        val registerData = RegisterData(
            id = binding.idET.text.toString(),
            pw = binding.pwET.text.toString(),
            name = binding.nameET.text.toString(),
            nickname = binding.nickNameET.text.toString(),
            email = binding.emailET.text.toString(),
        )
        val retrofit = RetrofitClient.initLocalRetrofit()
        val requestRegisterApi = retrofit.create(RetrofitClient.RegisterApi::class.java)
        requestRegisterApi.getRegisterData(registerData = registerData).enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                TODO("응답 코드가 성공일 시에 login Activity로 보내고," +
                        "실패 응답 코드에 따라 dialog")
                if(response.body()!!.isSuccess && response.body()!!.code == 1000){

                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}