package com.example.gukrule

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.fragment.FindPwFragment
import com.example.gukrule.fragment.LoginFragment
import com.example.gukrule.fragment.ModifyPwFragment
import com.example.gukrule.fragment.SignUpInfoFragment
import kotlinx.android.synthetic.main.fragment_login.*


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

    fun moveToModifyPw(userIdx : Int) {
        val bundle = Bundle()
        bundle.putInt("userIdx", userIdx)
        val modifyPwFragment = ModifyPwFragment()
        modifyPwFragment.arguments = bundle
        this.supportFragmentManager.beginTransaction().replace(R.id.empty_layout, modifyPwFragment).commit()
    }

    // 아이디 페스워드 버튼 클릭
    fun clickFindPwBtn() {
        this.supportFragmentManager.beginTransaction().replace(R.id.empty_layout, FindPwFragment()).commit()
    }

    fun moveToBack(pageIndex : Int) {
        var message = ""
        when (pageIndex) {
            1 -> message = "인증을 취소하시겠습니까?"
            2 -> message = "비밀번호 변경을 취소하시겠습니까?"
        }
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("확인",  DialogInterface.OnClickListener{ _, _ ->
                this.supportFragmentManager.beginTransaction().replace(R.id.empty_layout, LoginFragment()).commit()
            })
            .setNegativeButton("취소", null)
            .show()
    }

    fun moveToLogin() {
        this.supportFragmentManager.beginTransaction().replace(R.id.empty_layout, LoginFragment()).commit()
    }

}