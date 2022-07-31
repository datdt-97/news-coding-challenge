package com.datdt.news.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.datdt.news.data.model.Article

@Database(entities = [Article::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao
}