package com.datdt.news.data.source.remote.middleware

import com.datdt.news.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}
