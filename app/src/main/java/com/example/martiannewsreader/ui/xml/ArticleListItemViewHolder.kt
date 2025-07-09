package com.example.martiannewsreader.ui.xml

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.martiannewsreader.R

class ArticleListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val container: ConstraintLayout = itemView.findViewById(R.id.article_list_item_card)
    val title: TextView = itemView.findViewById(R.id.article_list_item_title)
    val image: ImageView = itemView.findViewById(R.id.article_list_item_image)
}
