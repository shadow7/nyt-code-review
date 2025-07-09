package com.example.martiannewsreader.ui.xml

import androidx.recyclerview.widget.DiffUtil
import com.example.martiannewsreader.model.domain.Article

class ArticleListDiffUtilCallback(
    private val newList: List<Article>, private val oldList: List<Article>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList.getOrNull(newItemPosition)?.id == oldList.getOrNull(oldItemPosition)?.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList.getOrNull(newItemPosition) == oldList.getOrNull(oldItemPosition)
}