package com.datdt.news.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(pattern: String = "dd/MM/yyyy"): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(parser.parse(this))
}
