package com.datdt.news.data.repository

import com.datdt.news.data.model.ArticlesResponse
import com.datdt.news.data.model.Result
import com.datdt.news.data.source.local.NewsLocalDataSourceType
import com.datdt.news.data.source.remote.NewsRemoteDataSourceType
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryType {
    suspend fun getNews(query: String, page: Int): Flow<Result<ArticlesResponse>>
}

class NewsRepository(
    private val local: NewsLocalDataSourceType,
    private val remote: NewsRemoteDataSourceType
) : NewsRepositoryType {
    override suspend fun getNews(query: String, page: Int): Flow<Result<ArticlesResponse>> {
        return remote.getNews(query = query, page = page)
    }
}
