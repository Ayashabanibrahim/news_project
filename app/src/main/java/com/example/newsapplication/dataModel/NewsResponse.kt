package com.example.newsapplication.dataModel

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)