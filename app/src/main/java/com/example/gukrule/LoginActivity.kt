package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.fragment.FindPwFragment
import com.example.gukrule.fragment.LoginFragment
import com.example.gukrule.fragment.SignUpInfoFragment


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_empty)

        supportFragmentManager.beginTransaction().replace(R.id.empty_layout, LoginFragment())
            .commit()


    }

    // 회원가입 페이지로 전환
    fun moveToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    // 아이디 페스워드 버튼 클릭
    fun clickFindPwBtn() {
        this.supportFragmentManager.beginTransaction().replace(R.id.empty_layout, FindPwFragment())
    }

    fun moveToBack() {
        this.supportFragmentManager.beginTransaction().replace(R.id.empty_layout, LoginFragment())
    }

}