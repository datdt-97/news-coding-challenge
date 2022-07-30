package com.datdt.news.di

import com.datdt.news.data.repository.NewsRepository
import com.datdt.news.data.repository.NewsRepositoryType
import com.datdt.news.data.source.local.NewsLocalDataSource
import com.datdt.news.data.source.local.NewsLocalDataSourceType
import com.datdt.news.data.source.remote.NewsRemoteDataSource
import com.datdt.news.data.source.remote.NewsRemoteDataSourceType
import org.koin.dsl.module

val repositoryModule = module {
    single<NewsLocalDataSourceType> { NewsLocalDataSource() }
    single<NewsRemoteDataSourceType> { NewsRemoteDataSource(get()) }
    single<NewsRepositoryType> { NewsRepository(get(), get()) }
}
