package com.example.newsapp.dataclasses

data class NewsDetails(
    val featured: Boolean,
    val id: Int,
    val image_url: String,
    val news_site: String,
    val published_at: String,
    val summary: String,
    val title: String,
    val updated_at: String,
    val url: String,
    val launches: List<LauncherDetails>,
    val events: List<EventDetails>
)