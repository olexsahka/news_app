package com.example.newsapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.NewsRepositoty
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponce
import com.example.newsapp.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SerachViewModel @Inject constructor(private val repositoty: NewsRepositoty) : ViewModel() {
    val searchLiveData: MutableLiveData<Resource<NewsResponce>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getSearchNews("rs")
    }

     fun getSearchNews(query: String){
        viewModelScope.launch {
            searchLiveData.postValue(Resource.Loading())
            val response = repositoty.searchNews(query,searchNewsPage)
            if (response.isSuccessful){
                response.body().let { res ->
                    searchLiveData.postValue(Resource.Success(res))
                }
            }
            else{
                searchLiveData.postValue(Resource.Error(response.message()))
            }
        }
    }
    fun addFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO){
        repositoty.addToFavourite(article)
    }

}