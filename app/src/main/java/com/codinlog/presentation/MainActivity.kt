package com.codinlog.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.codinlog.presentation.core.ApplicationViewModelStoreProvider
import com.codinlog.presentation.service.PresentationService
import com.codinlog.presentation.ui.theme.PresentationTheme

class MainActivity : ComponentActivity() {
    private val mServiceIntent by lazy {
        Intent(this, PresentationService::class.java)
    }

    private lateinit var mAppViewModel: ApplicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAppViewModel = ViewModelProvider(this, ApplicationViewModelFactory())[ApplicationViewModel::class.java]

        val data = buildList {
            add(HomeData("Start Service", fun() {
                startService(mServiceIntent)
            }))

            add(HomeData("Stop Service", fun() {
                stopService(mServiceIntent)
            }))

            add(HomeData("Show", fun() {
                mAppViewModel.setPresentationDialogState(PresentationDialogState.ServiceShowState)
            }))

            add(HomeData("Hide", fun() {
                mAppViewModel.setPresentationDialogState(PresentationDialogState.ServiceHideState)
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

    override fun getViewModelStore(): ViewModelStore = ApplicationViewModelStoreProvider
}

data class HomeData(val text: String, val onClick: () -> Unit)

@Composable
fun Home(data: List<HomeData>) {
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