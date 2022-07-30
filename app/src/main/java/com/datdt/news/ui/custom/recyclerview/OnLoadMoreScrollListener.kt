package com.datdt.news.ui.custom.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun interface OnLoadMoreListener {
    fun onLoadMore()
}

class OnLoadMoreScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val onLoadMoreListener: OnLoadMoreListener,
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    var isLoading = false
        private set
    private var lastVisibleItem = 0
    private var totalItemCount = 0

    fun setLoaded() {
        isLoading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        with(layoutManager) {
            totalItemCount = itemCount

            when (this) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions = findLastVisibleItemPositions(null)
                    // get maximum element within the list
                    lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions)
                }
                is GridLayoutManager -> {
                    lastVisibleItem = findLastVisibleItemPosition()
                }
                is LinearLayoutManager -> {
                    lastVisibleItem = findLastVisibleItemPosition()
                }
            }

            if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                onLoadMoreListener.onLoadMore()
                isLoading = true
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}
