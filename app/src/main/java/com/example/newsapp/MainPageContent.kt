package com.example.newsapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.newsapp.dataclasses.SerializableClass

class MainPageContent : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val seri_class = intent.getSerializableExtra("NewsDetails") as? SerializableClass
        seri_class?.let {

            setContent {
                Surface {
//                savedInstanceState.getSerializable


                }
            }
        }
    }
}