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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.martiannewsreader.R
import com.example.martiannewsreader.model.domain.Article

@Composable
fun ArticleListContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    articles: List<Article>,
    onArticleSelected: (index: Int) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier) {
            itemsIndexed(articles) { index, article ->
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
                Text(
                    text = article.title,
                    maxLines = 2,
                    fontSize = 21.sp,
                    modifier = Modifier.padding(8.dp)
                )
                GlideImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
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