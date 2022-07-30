package com.datdt.news.di

import com.datdt.news.BuildConfig
import com.datdt.news.data.source.remote.NewsApi
import com.datdt.news.data.source.remote.ServiceGenerator
import com.datdt.news.data.source.remote.middleware.NetworkInterceptor
import com.datdt.news.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

private fun buildHttpLog(): HttpLoggingInterceptor {
    val logLevel = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
    return HttpLoggingInterceptor().setLevel(logLevel)
}

val networkModule = module {
    single {
        ServiceGenerator.buildRestApi(
            Constants.BASE_URL,
            NewsApi::class.java,
            get(),
            listOf(
                buildHttpLog(),
                get<NetworkInterceptor>()
            )
        )
    }
}

val interceptorModule = module {
    single<Gson> {
        GsonBuilder().create()
    }

    single {
        NetworkInterceptor()
    }
}
