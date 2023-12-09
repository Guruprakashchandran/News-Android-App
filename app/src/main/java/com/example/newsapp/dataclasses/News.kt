package com.example.newsapp.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity//(tableName = "NewsDetails")
data class News(

    @PrimaryKey
    val id: Int,
    val featured: Boolean,
    val image_url: String,
    val news_site: String,
    val published_at: String,
    val summary: String,
    val title: String,
    val updated_at: String,
    val url: String,
    val launches_provider: String?,
    val launch_id : String?,
    val event_id: String?,
    var event_provider: String?
)
