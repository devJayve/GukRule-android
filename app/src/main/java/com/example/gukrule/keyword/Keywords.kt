package com.example.gukrule.keyword.data

import android.content.res.Resources
import com.example.gukrule.R

/* Returns initial list of flowers. */
fun flowerList(resources: Resources): List<Keyword> {
    return listOf(
        Keyword(
            id = 1,
            name = resources.getString(R.string.keyword1_name),
        ),
        Keyword(
            id = 2,
            name = resources.getString(R.string.keyword2_name),
        ),
        Keyword(
            id = 3,
            name = resources.getString(R.string.keyword3_name),
        ),
        Keyword(
            id = 4,
            name = resources.getString(R.string.keyword4_name),
        ),
        Keyword(
            id = 5,
            name = resources.getString(R.string.keyword5_name),
        ),
    )
}