package com.example.newsapp.database

sealed interface NewsEvent{

    object saveEvent: NewsEvent
}