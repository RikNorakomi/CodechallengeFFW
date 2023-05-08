package com.vanvelzen.codechallengeffw.android

import android.app.Application
import android.content.Context
import android.util.Log
import com.vanvelzen.codechallengeffw.AppInfo
import com.vanvelzen.codechallengeffw.initKoin
import com.vanvelzen.codechallengeffw.ui.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@MainApp }
                viewModel { OverviewViewModel(get(), get { parametersOf("OverviewViewModel") }) }
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
