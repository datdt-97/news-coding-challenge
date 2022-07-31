package com.datdt.news.di

import androidx.room.Room
import com.datdt.news.data.source.local.Database
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            Database::class.java,
            "article-db",
        ).build()
    }

    single { get<Database>().getArticleDao() }
}
