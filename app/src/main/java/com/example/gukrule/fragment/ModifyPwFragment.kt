package com.example.gukrule.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gukrule.LoginActivity
import com.example.gukrule.databinding.FragmentModifyPwBinding
import com.example.gukrule.retrofit.ModifyPwResponse
import com.example.gukrule.retrofit.PasswordData
import com.example.gukrule.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class ModifyPwFragment : Fragment() {

    private var _binding: FragmentModifyPwBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginActivity: LoginActivity
    private var isPwAcceptable = false
    private var isRePwAcceptable = false
    private var errorMsg = ""


    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModifyPwBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userIdx = arguments?.getInt("userIdx")!!
        
        binding.pwET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                pwExceptionHandling()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.rePwET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rePwExceptionHandling()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        binding.modifyPwBtn.setOnClickListener {
            if(isPwAcceptable && isRePwAcceptable) {
                modifyPw(passwordData = PasswordData(password = binding.pwET.text.toString(),
                passwordForCheck = binding.rePwET.text.toString(), userIdx = userIdx))
            } else {
                Toast.makeText(loginActivity, errorMsg, Toast.LENGTH_LONG).show()
            }
        }

        return root
    }

    // 비밀번호 예외처리
    private fun pwExceptionHandling() {
        isPwAcceptable = false

        if (binding.pwET.length() < 8) {
            errorMsg = "8자 이상 입력해주세요."
        } else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{8,20}$", binding.pwET.text)) {
            errorMsg = "비밀번호 형식이 올바르지 않습니다. (최소 8자, 영문자, 특수기호 모두 포함)"
        } else {
            errorMsg = ""
            isPwAcceptable = true
        }
    }

    // 비밀번호 재입력 예외처리
    private fun rePwExceptionHandling() {
        isRePwAcceptable = false

        Log.d("TAG", "pw : ${binding.pwET.text} rePw : ${binding.rePwET.text}")
        if (binding.pwET.text.toString() != binding.rePwET.text.toString()) {
            errorMsg = "비밀번호가 서로 일치하지 않습니다."
        } else {
            isRePwAcceptable = true
        }
    }

    private fun modifyPw(passwordData: PasswordData) {
        val modifyPwApi = RetrofitClient.initLocalRetrofit().create(
            RetrofitClient.AccountApi::class.java)
        modifyPwApi.postModifyPassword(passwordData = passwordData).enqueue(object : retrofit2.Callback<ModifyPwResponse> {
            override fun onResponse(
                call: Call<ModifyPwResponse>,
                response: Response<ModifyPwResponse>,
            ) {
                if(response.isSuccessful && response.body()!!.code == 1000) {
                    loginActivity.moveToLogin()
                    Toast.makeText(loginActivity, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ModifyPwResponse>, t: Throwable) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}