package com.example.martiannewsreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.martiannewsreader.model.domain.Article
import com.example.martiannewsreader.repository.ArticlesRepository
import com.example.martiannewsreader.util.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ArticlesRepository
) : ViewModel() {
    private val _articlesFlow = MutableStateFlow<List<Article>>(emptyList())
    private val _selectedArticleFlow = MutableStateFlow<Article?>(null)
    private val _selectedLanguageFlow = MutableStateFlow(Languages.ENGLISH)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoadingArticlesFlow = MutableStateFlow(false)
    private var selectedArticleIndex = -1

    val articlesFlow: StateFlow<List<Article>> = _articlesFlow
    val selectedArticleFlow: StateFlow<Article?> = _selectedArticleFlow
    val selectedLanguageFlow: StateFlow<Languages> = _selectedLanguageFlow
    val isLoadingArticlesFlow: StateFlow<Boolean> = _isLoadingArticlesFlow


    fun reloadArticles() {
        viewModelScope.launch {
            _isLoadingArticlesFlow.value = true
            val response = repository.getArticles(selectedLanguageFlow.value)

            if (response.isSuccess) {
                val responseArticles = response.getOrNull() ?: emptyList()
                _isLoadingArticlesFlow.value = false
                _articlesFlow.value = responseArticles
                if (selectedArticleIndex != -1 && responseArticles.size > selectedArticleIndex) {
                    _selectedArticleFlow.value = responseArticles[selectedArticleIndex]
                }
            } else {
                _isLoadingArticlesFlow.value = false
                _errorMessage.value = response.exceptionOrNull()?.message ?: "Unknown error"
            }
        }
    }

    fun selectArticle(index: Int, onArticleSelected: () -> Unit) {
        articlesFlow.value.let {
            if (it.size > index) {
                selectedArticleIndex = index
                _selectedArticleFlow.value = it[index]
                onArticleSelected()
            }
        }
    }

    fun setLanguage(language: Languages) {
        if (_selectedLanguageFlow.value != language) {
            _selectedLanguageFlow.value = language
            reloadArticles()
        }
    }
}
