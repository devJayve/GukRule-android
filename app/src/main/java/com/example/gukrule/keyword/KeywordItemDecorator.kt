package com.example.gukrule.keyword

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class KeywordItemDecorator(private val divWidth : Int) : RecyclerView.ItemDecoration() {

    @Override
    override fun getItemOffsets(outRect: Rect, view: View, parent : RecyclerView, state : RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = divWidth
        outRect.right = divWidth
    }
}