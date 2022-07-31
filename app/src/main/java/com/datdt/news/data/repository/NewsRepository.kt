package com.datdt.news.data.repository

import com.datdt.news.data.model.Article
import com.datdt.news.data.model.ArticlesResponse
import com.datdt.news.data.model.Result
import com.datdt.news.data.source.local.NewsLocalDataSourceType
import com.datdt.news.data.source.remote.NewsRemoteDataSourceType
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryType {
    suspend fun getNews(query: String, page: Int): Flow<Result<ArticlesResponse>>

    suspend fun getCachedNews(): List<Article>

    suspend fun saveCachedNews(data: List<Article>)
}

class NewsRepository(
    private val local: NewsLocalDataSourceType,
    private val remote: NewsRemoteDataSourceType
) : NewsRepositoryType {
    override suspend fun getNews(query: String, page: Int): Flow<Result<ArticlesResponse>> {
        return remote.getNews(query = query, page = page)
    }

    override suspend fun getCachedNews(): List<Article> {
        return local.getCacheNews()
    }

    override suspend fun saveCachedNews(data: List<Article>) {
        local.saveCache(data)
    }
}
