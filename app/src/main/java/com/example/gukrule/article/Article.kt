package com.example.gukrule.article

import androidx.annotation.DrawableRes

data class Article(
    var id: Int?,
    var budgetKey: String?,
    var title: String?,
    val image: String?,
    var content: String?,
    var date: String?,
)

