package com.datdt.news

import android.app.Application
import com.datdt.news.di.interceptorModule
import com.datdt.news.di.networkModule
import com.datdt.news.di.repositoryModule
import com.datdt.news.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(
                interceptorModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}
