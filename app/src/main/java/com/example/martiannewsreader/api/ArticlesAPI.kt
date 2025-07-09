package com.example.martiannewsreader.api

import com.example.martiannewsreader.model.data.ArticlesResponse
import com.example.martiannewsreader.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesAPI {
    @GET(value = Constants.ENGLISH_ARTICLES_URL)
    suspend fun getArticlesInEnglish(): Response<ArticlesResponse>

    @GET(value = Constants.MARTIAN_ARTICLES_URL)
    suspend fun getArticlesInMartian(): Response<ArticlesResponse>
}
