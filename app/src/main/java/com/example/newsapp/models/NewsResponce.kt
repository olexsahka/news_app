package com.example.newsapp.models

data class NewsResponce(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)