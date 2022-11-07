package com.example.gukrule.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
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
import com.raycoarana.codeinputview.CodeInputView

class FindPwFragment : Fragment() {

    private var _binding: FragmentFindPwBinding? = null

    private val binding get() = _binding!!

    private lateinit var loginActivity : LoginActivity
    private lateinit var mCountDownTimer : CountDownTimer
    private var isRunning = false

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



        return root
    }

    private fun sendCodeEvent() {
        if (binding.findPwIdET.text.isEmpty() || binding.findPwEmailET.text.isEmpty()) {
            Toast.makeText(loginActivity, "아이디 또는 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(loginActivity, "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
            binding.codeInputLayout.visibility = View.VISIBLE

            if (isRunning) {
                mCountDownTimer.cancel()
            }
            startCountDown()
        }

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
        loginActivity.moveToBack()
    }

    private fun setMailCodeView(mailCodeInput : CodeInputView) {
        if(mailCodeInput.code.toInt() == 123456) {
            mailCodeInput.setAnimateOnComplete(true)
            loginActivity.moveToBack()
            Toast.makeText(loginActivity, "이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show()
        }
        else {
            mailCodeInput.error = "인증번호가 올바르지 않습니다."
        }

        mailCodeInput.setEditable(true)
        mailCodeInput.code = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}