package com.example.newsapp.view

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.dataclasses.SerializableClass

@Composable
fun ContentView(navController: NavController, seri_class: SerializableClass?){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBarCreation(navController,seri_class)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCreation(navController: NavController, seri_class: SerializableClass?) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Today Trending News",
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.MainPage.route)
                            },

                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                                    id = R.string.arrow_back),
                                tint = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(R.color.light_white))
                )
            }
        ) {
                values ->
            LazyColumn(
                modifier = Modifier
                    .padding(values)
                    .background(Color.White)
                    .padding(10.dp)
            )
            {
                item {
                    NewsHeading(seri_class)
                }
            }
        }
    }
}

@Composable
fun NewsHeading(seri_class: SerializableClass?) {
    Column (
        modifier = Modifier.background(Color.White)
            .padding(10.dp)
    ){
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {

            Text(
                text = "${seri_class?.title}",
                style = TextStyle(
                    fontSize = 25.sp
                ),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Surface(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            color = Color.White
        ){
            val image_url = "${seri_class?.image_url}"
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                AndroidView(factory = { context ->
                    ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        Glide.with(context)
                            .load(image_url)
                            .into(this)
                    }
                })
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {
            Text(
                text = "             ${seri_class?.summary}",
                style = TextStyle(
                    fontSize =18.sp
                ),
                fontFamily = FontFamily.Serif,
                letterSpacing = 2.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Surface (
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ){
            Text(
                text = "more reference: ",
                style = TextStyle(
                    fontSize = 15.sp
                ),
                color = Color.Black,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ){
            val context = LocalContext.current
            val url = "${seri_class?.url}"
            val intent=Intent(
                Intent.ACTION_VIEW,
                Uri.parse(/* uriString = */ url)
            )
            Text(text = "${seri_class?.url}",
                color = Color.Blue,
                style = TextStyle(textDecoration = TextDecoration.Underline,
                    fontSize = 15.sp),
                modifier = Modifier.clickable {
                    context.startActivity(intent)
                }
            )
//            DisplayURL(url)
//            ClickableUnderlinedText(onClick = { openInChrome(url) },url)
//            val context= LocalContext.current
        }
        Spacer(modifier = Modifier.height(20.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {
            Text(
                text = "site : ${seri_class?.news_site}",
                style = TextStyle(
                    fontSize = 15.sp
                ),
                fontFamily = FontFamily.Serif,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {
            Text(
                text = "Published At - ${seri_class?.published_at}",
                style = TextStyle(
                    fontSize = 12.sp
                ),
                fontFamily = FontFamily.Serif,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {
            Text(
                text = "Updated At - ${seri_class?.updated_at}",
                style = TextStyle(
                    fontSize = 12.sp
                ),
                fontFamily = FontFamily.Serif,
                color = Color.Black
            )
        }
        if(seri_class?.launch_provider.equals("") == false) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = Color.White
            ) {
                Text(
                    text = "Launch Provider - ${seri_class?.launch_provider}",
                    style = TextStyle(
                        fontSize = 12.sp
                    ),
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
            }
        }
        if(seri_class?.event_provider.equals("")==false){

            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = Color.White
            ) {
                Text(
                    text = "Event Provider - ${seri_class?.event_provider}",
                    style = TextStyle(
                        fontSize = 12.sp
                    ),
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
            }
        }
    }
}
@Composable
fun DisplayURL(url: String) {
    Text(
        text = url,
        color = Color.Blue,
        modifier = Modifier.clickable {

        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickableUnderlinedText(onClick: () -> Unit,url: String) {
    var url_text by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    TextField(
        value = url_text,
        onValueChange = {it},
        placeholder = { Text(text = url)},
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 15.sp
        ),
        modifier = Modifier.
        background(Color.White).clickable {
            val intent=Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
            context.startActivity(intent)
        }
    )
//    Text(
//        buildAnnotatedString {
//            append(text)
//            addStyle(
//                style = SpanStyle(
//                    textDecoration = TextDecoration.Underline,
//                    color = Color.Blue,
//                    fontSize = 13.sp
//                ),
//                start = 0,
//                end = text.length
//            )
//        },
//        modifier = Modifier.clickable { onClick() },
//        style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
//    )
}

fun openInChrome(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.`package` = "com.android.chrome"
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}
