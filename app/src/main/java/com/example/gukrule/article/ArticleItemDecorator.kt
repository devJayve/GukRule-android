package com.example.gukrule.article

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/* RecyclerView.ItemDecoration() Subclassing */
class ArticleItemDecorator(
    private val divHeight: Int,
    private val divColor: Int = Color.TRANSPARENT
    ) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    @Override
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        recDivider(c, parent, color = divColor)
    }

    private fun recDivider(c: Canvas, parent: RecyclerView, color: Int) {
        paint.color = color

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams

            val divTop = child.bottom + param.bottomMargin
            val divBottom = divTop + divHeight

            c.drawRect(
                child.left.toFloat(),
                divTop.toFloat(),
                child.right.toFloat(),
                divBottom.toFloat(),
                paint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = divHeight
    }
}