package com.datdt.news.data.source.remote

import com.datdt.news.data.model.ArticlesResponse
import com.datdt.news.data.model.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface NewsRemoteDataSourceType {
    suspend fun getNews(query: String, page: Int): Flow<Result<ArticlesResponse>>
}

class NewsRemoteDataSource(private val api: NewsApi) : NewsRemoteDataSourceType {
    override suspend fun getNews(query: String, page: Int): Flow<Result<ArticlesResponse>> {
        return callbackFlow {
            trySendBlocking(api.getNews(query, page = page))
            awaitClose {  }
        }
    }
}
