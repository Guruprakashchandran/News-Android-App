package com.example.newsapp.view


sealed class Screen(val route: String){

//    fun withArgs(vararg data: String): String?{
//
//        return buildString {
//            append(route)
//            data.forEach { content ->
//                append("/${content}")
//            }
//        }
//    }
    object MainPage: Screen("main_page")
    object NewsPage: Screen("news_page")
}
