package com.datdt.news.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.datdt.news.data.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    suspend fun getAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<Article>)
}
