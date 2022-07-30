package com.datdt.news.data.source.remote

import com.datdt.news.data.model.ArticlesResponse
import com.datdt.news.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("searchIn") searchIn: String? = null,
        @Query("domains") domains: String? = null,
        @Query("excludeDomains") excludeDomains: String? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
    ): Result<ArticlesResponse>
}
