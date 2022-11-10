package com.example.gukrule.retrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.R
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.keyword.Keyword

class KeywordsAdapter() :
    ListAdapter<Keyword, KeywordsAdapter.KeywordViewHolder>(KeywordDiffCallback) {

    var keywordData = mutableListOf<Keyword>()

    /* ViewHolder for Keyword, takes in the inflated view */
    class KeywordViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
//        private val keywordButton : Button = itemView.keyword_circle
    }

    /* Creates and inflates view and return KeywordViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.keyword_circle, parent, false)
        return KeywordViewHolder(view)
    }

    /* keywords to bind view. */
    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val keywordCircle = holder.itemView.findViewById<TextView>(R.id.keyword_circle)
        var keywordText: String? = keywordData[position].name
        keywordCircle.text = keywordText!!.replace(" ", "\n")

        keywordCircle.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // (2) listener interface
    interface OnItemClickListener : ArticlesAdapter.OnItemClickListener {
        override fun onClick(v: View, position: Int)
    }

    // (3) click event
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener fun
    private lateinit var itemClickListener : ArticlesAdapter.OnItemClickListener

    override fun getItemCount(): Int {
        return keywordData.size
    }

}

object KeywordDiffCallback : DiffUtil.ItemCallback<Keyword>() {
    override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
        return oldItem.id == newItem.id
    }
}