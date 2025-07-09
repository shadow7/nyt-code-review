package com.example.martiannewsreader.repository

import com.example.martiannewsreader.api.ArticlesAPI
import com.example.martiannewsreader.model.data.ArticlesResponse
import com.example.martiannewsreader.model.domain.Article
import com.example.martiannewsreader.util.Languages
import com.example.martiannewsreader.util.NetworkResponseCodes
import okio.IOException
import javax.inject.Inject

class ArticlesRepository @Inject constructor(private val api: ArticlesAPI) {
    suspend fun getArticles(language: Languages): Result<List<Article>> =
        try {
            val response = when (language) {
                Languages.ENGLISH -> api.getArticlesInEnglish()
                Languages.MARTIAN -> api.getArticlesInMartian()
            }

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(parseResponse(it))
                } ?: Result.success(emptyList())
            } else {
                Result.failure(Exception(NetworkResponseCodes.REQUEST_ERROR.name))
            }
        } catch (e: IOException) {
            Result.failure(Exception(NetworkResponseCodes.CONNECTION_ERROR.name))
        }

    private fun parseResponse(response: ArticlesResponse): List<Article> =
        response.map {
            Article(
                id = it.id,
                title = it.title,
                body = it.body,
                imageUrl = if (it.images.isNotEmpty()) {
                    it.images[0].url
                } else {
                    ""
                }
            )
        }
}
