package com.example.gukrule.retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.R
import com.example.gukrule.article.Article

class ArticlesAdapter :
    ListAdapter<Article, ArticlesAdapter.ArticleViewHolder>(ArticleDiffCallback) {

    var articleData = mutableListOf<Article>()

    /* ViewHolder for Article, takes in the inflated view */
    class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val articleBudgetKeyView : Button = itemView.findViewById(R.id.article_keyword)
        private val articleTitleView : TextView = itemView.findViewById(R.id.article_title)
        private val articleImageView : ImageView = itemView.findViewById(R.id.article_image)
        private val articleContentView : TextView = itemView.findViewById(R.id.article_content)
        private val articleDateView : TextView = itemView.findViewById(R.id.article_date)

        fun bind(item: Article) {
            if (item.budgetKey != null) {
                articleBudgetKeyView.text = item.budgetKey
            } else {
                articleBudgetKeyView.text = ""
            }
            articleTitleView.text = item.title
            if (item.image != null) {
                articleImageView.setImageResource(item.image)
            } else {
                articleImageView.setImageResource(R.drawable.keyword_image)
            }
            articleContentView.text = item.content
            articleDateView.text = item.date
        }
    }

    /* Creates and inflates view and return ArticleViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    /* articles to bind view. */
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
//        val article = getItem(position)
//        holder.bind(article)
        holder.bind(articleData[position])
    }

    override fun getItemCount(): Int {
        return articleData.size
    }
}

object ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }
}