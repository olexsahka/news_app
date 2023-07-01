package com.example.newsapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.NewsRepositoty
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponce
import com.example.newsapp.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repositoty: NewsRepositoty) :ViewModel() {

    val newsLiveData: MutableLiveData<Resource<NewsResponce>> = MutableLiveData()
    var newsPage = 1

    init {
        getNews("ru")
    }

    private fun getNews(countryCode: String){
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repositoty.getNews(countryCode,newsPage)
            if (response.isSuccessful){
                response.body().let { res ->
                    newsLiveData.postValue(Resource.Success(res))
                }
            }
            else{
                newsLiveData.postValue(Resource.Error(response.message()))
            }
        }
    }
    fun addFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO){
        repositoty.addToFavourite(article)
    }
    fun getFavourite(){}
}
