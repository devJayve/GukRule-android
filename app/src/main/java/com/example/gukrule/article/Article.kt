package com.example.gukrule.article

import androidx.annotation.DrawableRes

data class Article(
    var id: Long,
    var budgetKey: String?,
    var title: String,
    @DrawableRes
    val image: Int?,
    var content: String,
    var date: String,
)