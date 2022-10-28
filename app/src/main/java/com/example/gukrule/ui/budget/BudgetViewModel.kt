package com.example.gukrule.ui.budget

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class BudgetViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "예산페이지 입니다."
    }
    val text: LiveData<String> = _text
}