package com.example.newsapp.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.NewsRepositoty
import com.example.newsapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val  repositoty: NewsRepositoty) :ViewModel() {

    init {
        getSavedArticles()
    }


    fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO){
        val res = repositoty.getFavoriteArticles()
        Log.d("count record", res.toString())
    }
    fun addFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO){
        repositoty.addToFavourite(article)
    }
}