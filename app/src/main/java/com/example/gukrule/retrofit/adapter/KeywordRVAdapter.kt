package com.example.gukrule.retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.R

class KeywordRVAdapter() : RecyclerView.Adapter<KeywordRVAdapter.KeywordViewHolder>() {

    private val keywordList = listOf(
        "국회행정지원 조사처 기본경비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
        "사무국\n 인건비",
    )

    class KeywordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.keyword_box)

        fun bind(title: String) {
            textView.text = "$title"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_keyword, parent, false)
        )
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(keywordList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener



    override fun getItemCount(): Int {
        return keywordList.size
    }
}
