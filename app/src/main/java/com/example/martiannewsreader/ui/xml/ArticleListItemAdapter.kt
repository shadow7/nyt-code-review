package com.example.martiannewsreader.ui.xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.martiannewsreader.R
import com.example.martiannewsreader.model.domain.Article


class ArticleListItemAdapter(
    private val initialArticles: List<Article>,
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<ArticleListItemViewHolder>() {
    private val articles = mutableListOf<Article>()

    init {
        articles.addAll(initialArticles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_item, parent, false)
        return ArticleListItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleListItemViewHolder, position: Int) {
        holder.title.text = articles[position].title
        Glide
            .with(holder.image)
            .load(articles[position].imageUrl)
            .into(holder.image)
        holder.container.setOnClickListener {
            onItemSelected(position)
        }
    }

    fun setItems(items: List<Article>) {
        val diffResult = DiffUtil.calculateDiff(ArticleListDiffUtilCallback(items, articles))
        diffResult.dispatchUpdatesTo(this)
        articles.clear()
        articles.addAll(items)
    }
}
