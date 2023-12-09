package com.example.newsapp.dataclasses

data class Details(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<NewsDetails>
)