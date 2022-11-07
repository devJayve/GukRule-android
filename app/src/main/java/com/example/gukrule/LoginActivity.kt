package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.databinding.ActivityLoginVisualBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginVisualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logIn_btn = findViewById<Button>(R.id.logIn_button)
        val sign_up_btn = findViewById<TextView>(R.id.sign_up)
        val find_id_btn = findViewById<TextView>(R.id.find_id_password)
//        로그인 버튼 클릭
        logIn_btn.setOnClickListener{}

//        회원가입 페이지 intent
        sign_up_btn.setOnClickListener{
//        val intent: Intent = Intent(this, ::class.java)
//        startActivity(intent)
        }

//        아이디/패스워드 버튼 클릭
        find_id_btn.setOnClickListener{
//        val intent: Intent = Intent(this, ::class.java)
//        startActivity(intent)
        }
    }

}