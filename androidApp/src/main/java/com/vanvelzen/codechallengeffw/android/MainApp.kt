package com.vanvelzen.codechallengeffw.android

import android.app.Application
import android.content.Context
import android.util.Log
import com.vanvelzen.codechallengeffw.AppInfo
import com.vanvelzen.codechallengeffw.initKoin
import com.vanvelzen.codechallengeffw.ui.DetailScreenViewModel
import com.vanvelzen.codechallengeffw.ui.OverviewScreenViewModel
import com.vanvelzen.codechallengeffw.ui.TopBarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@MainApp }
                viewModel { OverviewScreenViewModel(get(), get { parametersOf("StarWarsViewModel") }) }
                viewModel { DetailScreenViewModel(get(), get { parametersOf("DetailScreenViewModel") }) }
                viewModel { TopBarViewModel() }
                single<AppInfo> { AndroidAppInfo }
                single {
                    { Log.i("Startup", "Hello from the Android version!") }
                }
            }
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = "BuildConfig.APPLICATION_ID"
}
