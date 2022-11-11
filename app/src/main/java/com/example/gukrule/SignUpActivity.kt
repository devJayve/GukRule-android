package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gukrule.fragment.SelectArticleFragment
import com.example.gukrule.fragment.SignUpInfoFragment
import com.example.gukrule.retrofit.SelectedArticleJWT

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_empty)

        supportFragmentManager.beginTransaction().replace(R.id.empty_layout, SignUpInfoFragment())
            .commit()

//        supportFragmentManager.beginTransaction().replace(R.id.empty_layout, SelectArticleFragment().apply {
//            arguments = Bundle().apply {
//                putInt("userIdx", SignUpInfoFragment().)
//            }
//        })
    }

    fun setDataAtFragment(fragment: Fragment, userIdx: Int){
        val bundle = Bundle()
        bundle.putInt("userIdx", userIdx)

        fragment.arguments = bundle
        setFragment(fragment)
    }

    private fun setFragment(fragment: Fragment){
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.empty_layout, fragment)
        transaction.commit()
    }

    fun transFragEvent(transNum : Int, userIdx: Int?, jwt: String?) {
        val transaction = this.supportFragmentManager.beginTransaction()

        when(transNum) {
            0 -> transaction.replace(R.id.empty_layout, SignUpInfoFragment())
            2 -> {
                Log.d("LOG", "user index : $userIdx")
                val bundle = Bundle()
                bundle.putInt("userIdx", userIdx!!)
                bundle.putString("jwt", jwt!!)
                val fragment = SelectArticleFragment()
                fragment.arguments = bundle
                transaction.replace(R.id.empty_layout, fragment)
            }
            10 -> {
                val loginIntent = Intent(this, LoginActivity::class.java) // 로그인 페이지로 전환
                startActivity(loginIntent)
            }
        }
        transaction.commit()
    }

    fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java) // 로그인 페이지로 전환
        startActivity(intent)
    }

    fun showToastEvent(text : String, isShort : Boolean) {
        if (isShort) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }


}