package com.codinlog.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codinlog.presentation.ui.theme.PresentationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val data = buildList {
            add(HomeData("Service + Compose",fun(){
                
            }))
            
            add(HomeData("Service + ViewPager2",fun(){
                
            }))
            
            add(HomeData("Service + RemoteView",fun(){
                
            }))
            
            add(HomeData("Service + FrameLayout",fun(){
                
            }))
            
            add(HomeData("Activity + Fragment",fun(){
                
            }))
        }

        setContent {
            PresentationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(data = data)
                }
            }
        }
    }
}


data class HomeData(val text:String,val onClick:()->Unit)

@Composable
fun Home(data:List<HomeData>){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        data.forEach {
            Button(onClick = { it.onClick() }) {
                Text(text = it.text)
            }
        }
    }
}