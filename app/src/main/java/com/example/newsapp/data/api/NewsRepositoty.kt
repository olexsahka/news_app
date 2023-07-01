package com.example.newsapp.data.api

import com.example.newsapp.data.db.ArticleDao
import com.example.newsapp.models.Article
import retrofit2.http.Query
import javax.inject.Inject

class NewsRepositoty @Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao) {
    suspend fun getNews(countruCode : String, pageNumber : Int) =
        apiService.getHeadLines(countruCode,pageNumber)

    suspend fun searchNews(query: String,pageNumber: Int) =
        apiService.getEverything(query,pageNumber)


    fun getFavoriteArticles() = articleDao.getArticles()
    suspend fun addToFavourite(article: Article) = articleDao.insertArticle(article)
    suspend fun deleteToFavourite(article: Article) = articleDao.deleteArticle(article)

}