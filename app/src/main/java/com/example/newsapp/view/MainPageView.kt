package com.example.newsapp.view

import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.dataclasses.Details
import com.example.newsapp.dataclasses.SerializableClass
import com.example.newsapp.interfaces.NewsInterface
import com.example.newsapp.retrofit.RetrofitHelper
import retrofit2.Response


@Composable
fun MainPageView() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainPage.route){
        composable(Screen.MainPage.route){
                MainScreen(navController = navController)
        }
//        composable(
//            route = "news_page/{details}",
//                    arguments = listOf(navArgument("details") { defaultValue = "" })
//        ){entry -> ContentView(navController,entry.arguments?.getSerializable("details").toString())
//        }
//        composable("news_page/{id}/{title}/{url}/{image_url}/{news_site}/{summary}/{published_at}/{updated_at}/{featured}/{launch_id}/{launch_provider}/{event_id}/{event_provider}"){
//
//                backStackEntry ->
////            try {
//                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
//                val title = backStackEntry.arguments?.getString("title") ?:""
//                val url = backStackEntry.arguments?.getString("url") ?:""
//                val image_url = backStackEntry.arguments?.getString("image_url") ?:""
//                val news_site = backStackEntry.arguments?.getString("news_site") ?:""
//                val summary = backStackEntry.arguments?.getString("summary") ?:""
//                val published_at = backStackEntry.arguments?.getString("published_at") ?:""
//                val updated_at = backStackEntry.arguments?.getString("updated_at") ?:""
//                val featured = backStackEntry.arguments?.getString("featured").toBoolean() ?:false
//                val launch_id = backStackEntry.arguments?.getString("launch_id") ?:""
//                val launch_provider = backStackEntry.arguments?.getString("launch_provider") ?:""
//                val event_id = backStackEntry.arguments?.getString("event_id")?.toIntOrNull() ?:0
//                val event_provider = backStackEntry.arguments?.getString("event_provider") ?:""
//                val data = SerializableClass(id,title,url,image_url,news_site, summary, published_at, updated_at, featured, launch_id, launch_provider, event_id, event_provider)
//
//                ContentView(data)
////            }
//            catch (e: Exception){
//
//                Text("Error : ${e.message}")
//            }
        composable(Screen.NewsPage.route){
            val seri_class = navController.previousBackStackEntry?.savedStateHandle?.get<SerializableClass>(
                "serializable_class"
            )
            Log.d("title",seri_class?.title.toString())
            ContentView(navController = navController,seri_class)
        }
//        }
    }
}




//@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(navController: NavController) {

    val resultState = remember {
        mutableStateOf<Response<Details>?>(null)
    }
    val dps = RetrofitHelper.getInstance().create(NewsInterface::class.java)
    var result: Response<Details>
    LaunchedEffect(Unit) {
        result = dps.getArticles("json", 20, 20)
        resultState.value = result
    }
    val data = resultState.value?.body()
    if (data != null) {
        Greeting(navController = navController,name = data)
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
//    val context = LocalContext.current
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
                    .load(imageUrl)
                    .into(this)
            }
        })
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(navController: NavController,name: Details?) {
    Log.d("Greeting",name.toString())
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
                        .padding(values)
                        .background(Color.White),
                ) {
                    item {
                        ShowNews(navController= navController,name)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowNews(navController: NavController,name: Details) {

//    val context = LocalContext.current as ComponentActivity
//    val intent = Intent(context,MainPageContent::class.java)
    val colorFromXml = colorResource(id = R.color.light_white)
    for (data in name.results){
//        val title = data.title
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
                            fontSize =22.sp
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
                    ) {
                        Text(
                            text = data.news_site,
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
                        onClick = {

//                            Log.d("title",data.title)
//                            navController.navigate(Screen.NewsPage.withArgs(data.id.toString()).toString())
//                            var details = Gson().toJson(seri_class)
                            val launch_id : String
                            val launch_provider: String
                            val event_id : Int
                            val event_provider: String
                            if(data.launches.size==0||data.launches.isEmpty()){

                                launch_id = ""
                                launch_provider = ""
                            }
                            else {
                                launch_id = data.launches.get(0).launch_id
                                launch_provider = data.launches.get(0).provider
                            }
                            if(data.events.size == 0||data.events.isEmpty()){

                                event_id = 0
                                event_provider = ""
                            }
                            else {
                                event_id = data.events.get(0).event_id
                                event_provider = data.events.get(0).provider
                            }
//                            Log.d("output",launch_id+event_id)
//                            navController.navigate("news_page/${data.id}/${data.title}/${data.url}/${data.image_url}/${data.news_site}/${data.summary}/${data.published_at}/${data.updated_at}/${data.featured}/${launch_id}/${launch_provider}/${event_id}/${event_provider}")
                            var seri_class = SerializableClass(data.id,data.title,data.url,data.image_url,data.news_site,data.summary,data.published_at,data.updated_at,data.featured,launch_id,launch_provider,event_id,event_provider)
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key="serializable_class",
                                value = seri_class
                            )
                            navController.navigate(Screen.NewsPage.route)
                        }
                    ){
                        Text(
                            text = "Read More",
                            modifier= Modifier
                                .background(Color.Gray)
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