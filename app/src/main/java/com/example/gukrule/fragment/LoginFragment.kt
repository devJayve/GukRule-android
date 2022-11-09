package com.example.gukrule.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gukrule.LoginActivity
import com.example.gukrule.MainActivity
import com.example.gukrule.databinding.FragmentLoginBinding
import com.example.gukrule.retrofit.LoginData
import com.example.gukrule.retrofit.LoginResponse
import com.example.gukrule.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginActivity: LoginActivity
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.signUp.setOnClickListener{
            loginActivity.moveToSignUp()
        }

        binding.findIdPassword.setOnClickListener{
            loginActivity.clickFindPwBtn()
        }

        binding.logInButton.setOnClickListener{
            loginPress()
        }

        return root
    }

    fun loginPress(){
        if(binding.id.text.isEmpty() || binding.password.text.isEmpty()){
            Toast.makeText(loginActivity, "아이디 또는 비밀번호란이 비어있습니다", Toast.LENGTH_SHORT).show()
        }else{

        }
    }

    private fun connectLoginApi() {
        val loginData = LoginData(
            id = binding.id.text.toString(),
            pw = binding.password.text.toString()
        )

        val retrofit = RetrofitClient.initLocalRetrofit()
        val loginApi = retrofit.create(RetrofitClient.LoginApi::class.java)
        loginApi.getLoginData(loginData = loginData)
            .enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.body()!!.isSuccess && response.body()!!.code == 1000) {
                    val intent = Intent(loginActivity, MainActivity::class.java) // 로그인 페이지로 전환
                    startActivity(intent)
                    response.body()!!.result
                    //TODO::추후에 id, token preference Util 설정
                }

                Log.d("success", response.body()!!.code.toString())
                Log.d("success", response.body()!!.message)
                Log.d("success", response.body()!!.result.toString())
                Log.d("success", "로그인 성공 : ${response.body()!!.isSuccess}")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("failure", "등록되지 않은 회원입니다")
            }
        })
    }
}