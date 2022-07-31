package com.datdt.news.data.source.local

import com.datdt.news.data.model.Article

interface NewsLocalDataSourceType {
    suspend fun getCacheNews(): List<Article>

    suspend fun saveCache(data: List<Article>)
}

class NewsLocalDataSource(private val dao: ArticleDao) : NewsLocalDataSourceType {
    override suspend fun getCacheNews(): List<Article> {
        return dao.getAll()
    }

    override suspend fun saveCache(data: List<Article>) {
        dao.insertAll(data)
    }
}
