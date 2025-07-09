package com.example.martiannewsreader.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.preference.PreferenceManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.martiannewsreader.R
import com.example.martiannewsreader.model.domain.Article

@Composable
fun ArticleListContent(
    modifier: Modifier = Modifier,
    shouldShowSavedArticles: Boolean,
    isLoading: Boolean,
    articles: List<Article>,
    onArticleSelected: (index: Int) -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val shownArticles = mutableListOf<Article>()
    if (shouldShowSavedArticles) {
        articles.forEach { article ->
            if (sharedPreferences.getBoolean(article.id, false)) {
                shownArticles.add(article)
            }
        }
    } else {
        shownArticles.addAll(articles)
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier) {
            itemsIndexed(shownArticles) { index, article ->
                ArticlePreview(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                        .clickable {
                            onArticleSelected(index)
                        },
                    article = article,
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticlePreview(
    modifier: Modifier = Modifier,
    article: Article,
) {
    val context = LocalContext.current
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var isSaved by remember(article.id) {
        mutableStateOf(sharedPreferences.getBoolean(article.id, false))
    }
    val bookmarkResource by remember(isSaved) {
        mutableIntStateOf(
            if (isSaved) {
                R.drawable.ic_bookmark_filled
            } else {
                R.drawable.ic_bookmark_unfilled
            }
        )
    }
    val imageUrl by remember(article.id) {
        mutableStateOf(article.imageUrl)
    }

    Card(
        elevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.title,
                        maxLines = 2,
                        fontSize = 21.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    Image(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .clickable {
                                isSaved = !isSaved
                                sharedPreferences
                                    .edit()
                                    .putBoolean(article.id, isSaved)
                                    .apply()
                            },
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = bookmarkResource),
                        contentDescription = "Favorite"
                    )
                }

                GlideImage(
                    model = imageUrl,
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_list_item_arrow),
                contentDescription = "Open Details View for ${article.title}",
            )
        }
    }
}