package com.example.martiannewsreader.model.domain

data class Article(
    val id: String,
    val title: String,
    val body: String,
    val imageUrl: String
) {
    override fun equals(other: Any?): Boolean =
        other is Article &&
            id == other.id &&
            title == other.title &&
            body == other.body &&
            imageUrl == other.imageUrl
}
