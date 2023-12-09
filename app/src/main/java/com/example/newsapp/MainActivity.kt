package com.example.newsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.newsapp.dataclasses.Details
import com.example.newsapp.dataclasses.SerializableClass
import com.example.newsapp.interfaces.NewsInterface
import com.example.newsapp.retrofit.RetrofitHelper
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.view.DisplayResult
import com.example.newsapp.view.LoadImageWithGlide
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private val resultState = mutableStateOf<Response<Details>?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var result: Response<Details>
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
//                    color = MaterialTheme.colorScheme.background
                ) {

                    var dps = RetrofitHelper.getInstance().create(NewsInterface::class.java)

                    LaunchedEffect(Unit) {
                        val result = dps.getArticles("json", 20, 20)
                        resultState.value = result
                    }
                    DisplayResult(resultState.value)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowNews(name: Details) {

    val context = LocalContext.current as ComponentActivity
    val intent = Intent(context,MainPageContent::class.java)
//    val button_backgroundcolor=Color()
    val colorFromXml = colorResource(id = R.color.light_white)
    for (data in name.results){

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(1.dp, Color.Black)
                .padding(10.dp)
                .padding(bottom = 1.dp),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                ) {
                    LoadImageWithGlide(data.image_url)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color= Color.White
                    ,
                ) {
                    Text(
                        text=data.title,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        style = TextStyle(
                            fontSize =30.sp
                        ),
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),

                    ){

                    Surface (
                        color = Color.White,
                        modifier= Modifier.width(200.dp)
                    ){
                        Text(text = data.news_site,
                            modifier = Modifier.padding(10.dp),
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Surface (
                        color= Color.White,
                        shape = MaterialTheme.shapes.small,
//                        onClick = {
//                            val seri_class = SerializableClass(data.id,data.title,data.url,data.image_url,data.news_site,data.summary,data.published_at,data.updated_at,data.featured,data.launches?.get(0)?.launch_id,data.launches?.get(0)?.provider,data.events?.get(0)?.event_id,data.events?.get(0)?.provider)
//                            intent.putExtra("NewsDetails",seri_class)
//                            context.startActivity(intent)
//                        }
                    ){
                        Text(
                            text = "Read More",
                            modifier= Modifier.background(Color.Gray)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            style = TextStyle(
                                fontSize=20.sp
                            ),
                            color=colorFromXml
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
        LoadImageWithGlide(imageUrl = "https://spacenews.com/pentagon-advisors-despite-reforms-space-force-still-shackled-to-sluggish-procurement-system/")
    }
}

