package com.datdt.news.data.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import androidx.room.*

import kotlinx.parcelize.Parcelize
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity
@Parcelize
data class Article(
    @PrimaryKey
    val id: Int,
    @SerializedName("source")
    @Embedded()
    val source: Source,
    @SerializedName("author")
    @ColumnInfo(name = "author")
    val author: String,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,
    @SerializedName("urlToImage")
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String,
    @SerializedName("publishedAt")
    @ColumnInfo(name = "publishAt")
    val publishedAt: String,
    @SerializedName("content")
    @ColumnInfo(name = "content")
    val content: String
) : Parcelable {

    override fun hashCode(): Int {
        var result = title.hashCode()
        if (url.isEmpty()) {
            result = 31 * result + url.hashCode()
        }
        return result
    }
}

@Parcelize
data class Source(
    @SerializedName("id")
    val sourceId: String?,
    @SerializedName("name")
    val name: String
) : Parcelable
