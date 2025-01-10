package com.example.gukrule.adapter

import android.annotation.SuppressLint
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
import com.example.gukrule.R
import com.example.gukrule.article.Article

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    var articleData = ArrayList<Article>()

    class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val articleBudgetKeyView : Button = itemView.findViewById(R.id.article_keyword)
        private val articleTitleView : TextView = itemView.findViewById(R.id.article_title)
        private val articleImageView : ImageView = itemView.findViewById(R.id.article_image)
        private val articleContentView : TextView = itemView.findViewById(R.id.article_content)
        private val articleDateView : TextView = itemView.findViewById(R.id.article_date)

        fun bind(item: Article) {
            articleBudgetKeyView.text = item.budgetKey
            articleTitleView.text = item.title
            Glide.with(articleImageView.context)
                .load(item.image)
                .error(R.drawable.img_load_failed)
                .into(articleImageView)
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
        holder.bind(articleData[position])

    // (1) item click
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // (2) listener interface
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    // (3) click event
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener fun
    private lateinit var itemClickListener : OnItemClickListener

    override fun getItemCount(): Int {
        return articleData.size
    }
}




