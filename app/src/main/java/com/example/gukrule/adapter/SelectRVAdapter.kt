package com.example.gukrule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.R

class SelectRVAdapter() : RecyclerView.Adapter<SelectRVAdapter.SelectViewHolder>() {

    private val titleList = listOf(
        "기사 제목 1",
        "기사 제목 2",
        "기사 제목 3",
        "기사 제목 4",
        "기사 제목 5",
        "기사 제목 6",
        "기사 제목 7",
        "기사 제목 8",
        "기사 제목 9",
        "기사 제목 10",
    )

    class SelectViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.select_article_text)
        val imageView: ImageView = view.findViewById(R.id.select_article_image)

        fun bind(title: String){
            textView.text = "$title"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        return SelectViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_article,parent,false)
        )
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.bind(titleList[position])

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it,position)
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
        return titleList.size
    }

}