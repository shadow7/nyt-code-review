package com.example.martiannewsreader.model.data

import com.squareup.moshi.Json

data class Image(
    val height: Int,
    @Json(name = "top_image")
    val topImage: Boolean,
    val url: String,
    val width: Int
)
