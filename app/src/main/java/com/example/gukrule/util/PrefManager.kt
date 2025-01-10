package com.example.gukrule.util

import android.content.Context


object PrefManager {
    private const val SHARED_ACCOUNT = "shared_account"
    private const val KEY_ACCOUNT = "key_account"

    fun storeUserToken(userToken: String?) {
        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()
        editor.putString(KEY_ACCOUNT, userToken)
        editor.apply()
    }

    fun storeUserIdx(userIdx: Int) {
        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        // 쉐어드 에디터 가져오기
        val editor = shared.edit()
        editor.putInt(KEY_ACCOUNT, userIdx)
        editor.apply()
    }

    fun getUserToken(): String {
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        return shared.getString(KEY_ACCOUNT, "").toString()
    }

    fun getUserIdx(): Int {
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        return shared.getInt(KEY_ACCOUNT, 0)
    }


    fun deleteUserToken() {
        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        val editor = shared.edit()
        editor.remove(KEY_ACCOUNT)
        editor.apply()
    }

    fun deleteUserIdx() {
        // 쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_ACCOUNT, Context.MODE_PRIVATE)

        val editor = shared.edit()
        editor.remove(KEY_ACCOUNT)
        editor.apply()
    }

}