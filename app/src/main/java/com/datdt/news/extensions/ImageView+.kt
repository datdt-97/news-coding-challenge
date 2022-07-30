package com.datdt.news.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.datdt.news.R

fun ImageView.loadImage(url: String?) =
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_error)
        .placeholder(R.drawable.ic_loading)
        .into(this)

fun ImageView.loadCircleImage(url: String?) =
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_error)
        .placeholder(R.drawable.ic_loading)
        .circleCrop()
        .into(this)
