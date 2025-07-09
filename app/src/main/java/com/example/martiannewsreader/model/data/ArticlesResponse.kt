package com.example.martiannewsreader.model.data

typealias ArticlesResponse = List<ArticlesResponseItem>

data class ArticlesResponseItem(
    val body: String,
    val id: String,
    val images: List<Image>,
    val title: String
)