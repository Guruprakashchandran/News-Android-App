package com.example.newsapp.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class SerializableClass (

    val id: Int,
    val title: String,
    val url: String,
    val image_url: String,
    val news_site: String,
    val summary: String,
    val published_at: String,
    val updated_at: String,
    val featured: Boolean,
    val launch_id: String?,
    val launch_provider: String?,
    val event_id: Int?,
    val event_provider: String?
): Parcelable
