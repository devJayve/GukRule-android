package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.fragment.SelectArticleFragment
import com.example.gukrule.fragment.SignUpInfoFragment

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_empty)

        supportFragmentManager.beginTransaction().replace(R.id.empty_layout, SignUpInfoFragment())
            .commit()
    }

    fun transFragEvent(transNum : Int) {
        val transaction = this.supportFragmentManager.beginTransaction()

        when(transNum) {
            0 -> transaction.replace(R.id.empty_layout, SignUpInfoFragment())
//            1 -> transaction.replace(R.id.empty_layout, SignUpInfoFragment())
            2 -> transaction.replace(R.id.empty_layout, SelectArticleFragment())
            10 -> {
                val loginIntent = Intent(this, LoginActivity::class.java) // 로그인 페이지로 전환
                startActivity(loginIntent)
            }
        }
        transaction.commit()
    }

    fun showToastEvent(text : String, isShort : Boolean) {
        if (isShort) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }


}