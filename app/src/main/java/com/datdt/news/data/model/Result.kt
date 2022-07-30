package com.datdt.news.data.model

sealed interface Result<out T> {
    data class Success<T>(val value: T): Result<T>
    data class Error(val error: Throwable): Result<Nothing>
}
