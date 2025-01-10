package com.example.gukrule.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gukrule.ArticleVisualActivity
import com.example.gukrule.NewsActivity
import com.example.gukrule.R
import com.example.gukrule.article.Article

@SuppressLint("NotifyDataSetChanged")
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var articleData = ArrayList<Article>()

    fun initializeArticle(article : Article) {
        articleData.add(article)
        notifyDataSetChanged()
    }

    fun addArticleData(article : Article) {
        articleData.add(article)
    }

    /* ViewHolder for Article, takes in the inflated view */
    class NewsViewHolder(itemView: View) :
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
            Glide.with(articleImageView.context)
                .load(item.image)
                .into(itemView.findViewById(R.id.article_image))
            articleContentView.text = item.content
            articleDateView.text = item.date
        }
    }

    /* Creates and inflates view and return NewsViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    /* news to bind view. */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articleData[position])
    }

    override fun getItemCount(): Int {
        return articleData.size
    }
}
