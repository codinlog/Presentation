package com.codinlog.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.codinlog.presentation.core.ApplicationViewModelStoreProvider
import com.codinlog.presentation.service.PresentationService
import com.codinlog.presentation.ui.theme.PresentationTheme

class MainActivity : ComponentActivity() {
    private val mServiceIntent by lazy {
        Intent(this, PresentationService::class.java)
    }

    private var mPresentationBinder: IPresentationAidlInterface? = null

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mPresentationBinder = IPresentationAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mPresentationBinder = null
        }

    }

    private lateinit var mAppViewModel: ApplicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAppViewModel =
            ViewModelProvider(this, ApplicationViewModelFactory())[ApplicationViewModel::class.java]

        val permission = checkSelfPermission(android.Manifest.permission.CAMERA)
        if (permission != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 1)
        }

        val data = buildList {
            add(HomeData("Start Service", fun() {
                startService(mServiceIntent)
                bindService()
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

            add(HomeData("Anim", fun() {
               mAppViewModel.setPresentationScreenState(PresentationScreenRoute.AnimScreen)
            }))

            add(HomeData("First", fun() {
                mAppViewModel.setPresentationScreenState(PresentationScreenRoute.FirstScreen)
            }))
            add(HomeData("Second", fun() {
                mAppViewModel.setPresentationScreenState(PresentationScreenRoute.SecondScreen)
            }))
            add(HomeData("Camera", fun() {
                mAppViewModel.setPresentationScreenState(PresentationScreenRoute.CameraScreen)
            }))
            add(HomeData("Keyboard", fun() {
                startActivity(Intent(this@MainActivity, KeyboardActivity::class.java))
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

    private fun bindService() {
        bindService(mServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }
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