package com.example.gukrule.ui.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class FeedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "피드 페이지 입니다."
    }
    val text: LiveData<String> = _text
}