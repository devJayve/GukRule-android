package com.example.gukrule.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gukrule.LoginActivity
import com.example.gukrule.MainActivity
import com.example.gukrule.databinding.FragmentFindPwBinding
import com.example.gukrule.retrofit.AccountData
import com.example.gukrule.retrofit.RetrofitClient
import com.example.gukrule.retrofit.SmsResponse
import com.raycoarana.codeinputview.CodeInputView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class FindPwFragment : Fragment() {

    private var _binding: FragmentFindPwBinding? = null

    private val binding get() = _binding!!

    private lateinit var loginActivity : LoginActivity
    private lateinit var mCountDownTimer : CountDownTimer
    private var isRunning = false
    private var authCode = 0
    private var userIdx = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindPwBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //listener
        binding.sendCodeBtn.setOnClickListener {
            sendCodeEvent() // 이메일로 인증 코드 전송
        }

        binding.backPageBtn.setOnClickListener {
            moveBackPageEvent()
        }

        binding.codeInputView.addOnCompleteListener {
            setMailCodeView()
        }



        return root
    }

    private fun sendCodeEvent() {
        val phoneNumber = binding.findPwEmailET.text
        val regex = Regex("[^A-Za-z0-9]")
        phoneNumber.apply {
            regex.replace(phoneNumber, "")
            filter { !it.isWhitespace()
            }
            if (binding.findPwIdET.text.isEmpty() || binding.findPwEmailET.text.isEmpty()) {
                Toast.makeText(loginActivity, "아이디 또는 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if(!phoneNumber.startsWith("010") || !(phoneNumber.length == 11)) {
                Toast.makeText(loginActivity, "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                Log.d("LOG","phone number : ${binding.findPwEmailET.text.toString()}")
                hostSendSmsApi(AccountData(id = binding.findPwIdET.text.toString(), phone = binding.findPwEmailET.text.toString()))
            }
        }

    }

    private fun hostSendSmsApi(accountData: AccountData) {
        val sendSMSApi = RetrofitClient.initLocalRetrofit().create(RetrofitClient.AccountApi::class.java)
        sendSMSApi.postSendSms(accountData = accountData).enqueue(object : Callback<SmsResponse>{
            override fun onResponse(call: Call<SmsResponse>, response: Response<SmsResponse>) {
                Toast.makeText(loginActivity, "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
                Log.d("LOG", "resonse code : ${response.body()!!.code}")

                binding.codeInputLayout.visibility = View.VISIBLE
                if (isRunning) {
                    mCountDownTimer.cancel()
                }
                startCountDown()
                authCode = response.body()!!.result.authCode.toInt()
                userIdx = response.body()!!.result.userIdx
                Log.d("LOG", "auth code : $authCode")


            }
            override fun onFailure(call: Call<SmsResponse>, t: Throwable) {

            }

        })
    }

    private fun startCountDown() {
        isRunning = true

        mCountDownTimer = object : CountDownTimer(180000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(time: Long) {
                val duration = time/1000
                val sec = duration % 60
                val min = duration / 60
                if (sec >= 10) {
                    binding.timeTV.text = "${min}:${sec}"
                }
                else {
                     binding.timeTV.text = "${min}:0${sec}"
                }
            }

            override fun onFinish() {
                isRunning = false
                binding.codeInputLayout.visibility = View.INVISIBLE
            }
        }
        mCountDownTimer.start()
    }

    private fun moveBackPageEvent() {
        if (isRunning) {
            mCountDownTimer.cancel()
        }
        loginActivity.moveToBack(2)
    }

    private fun setMailCodeView() {
        if(binding.codeInputView.code.toInt() == authCode) {
            if (isRunning) {
                mCountDownTimer.cancel()
            }
            binding.codeInputView.setAnimateOnComplete(true)
            loginActivity.moveToModifyPw(userIdx)
        }
        else {
            binding.codeInputView.error = "인증번호가 올바르지 않습니다."
        }

        binding.codeInputView.setEditable(true)
        binding.codeInputView.code = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}