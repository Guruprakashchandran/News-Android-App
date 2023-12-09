package com.example.newsapp.view

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.newsapp.MainPageContent
import com.example.newsapp.R
import com.example.newsapp.ShowNews
import com.example.newsapp.dataclasses.Details
import retrofit2.Response


@Composable
fun DisplayResult(result: Response<Details>?) {

    val data = result?.body()
    if (data != null) {
        Greeting(name = data)
    } else {
        LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Text(
        text = "Loading...",
        color = Color.White
    )
}
@Composable
fun LoadImageWithGlide(imageUrl: String) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
//        .fillMaxWidth()
//        .height(100.dp)
//        .padding(0.dp)
//        .padding(10.dp)
//        .background(Color.White)
    ) {
        AndroidView(factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
//                scaleType = ImageView.ScaleType.FIT_CENTER
                // Load image using Glide
                Glide.with(context)
                    .load(imageUrl)
                    // Optional: placeholder and error
                    //.placeholder(R.drawable.placeholder)
                    //.error(R.drawable.error)
                    .into(this)
            }
        })
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: Details?) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        if (name == null) {
            Text(
                text = "Loading!!!",
                color = Color.White
            )
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "NEWS  APP",
                                color= Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(
                            R.color.light_white)
                        ),
                    )
                }
            ) { values ->
                LazyColumn(
                    modifier = Modifier
//                    .fillMaxSize()
                        .padding(values)
                        .background(Color.White),
                ) {
                    item {
                        ShowNews(name)
                    }
                }
            }
        }
    }
}


