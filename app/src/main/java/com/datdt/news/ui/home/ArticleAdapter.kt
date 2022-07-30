package com.datdt.news.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.datdt.news.data.model.Article
import com.datdt.news.databinding.ItemArticleBinding
import com.datdt.news.databinding.ItemLoadMoreBinding
import com.datdt.news.extensions.loadImage

const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_LOADING = 1

class ArticleAdapter(
    private val items: MutableList<Article?>,
    private val onArticleClicked: (Article) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_ITEM) {
            ArticleViewHolder(
                ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onArticleClicked,
            )
        } else {
            LoadMoreViewHolder(
                ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            items[position]?.let((holder as ArticleViewHolder)::setData)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) =
        if (items[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM

    fun addLoadingView() {
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoadingView() {
        if (items.size != 0) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size)
        }
    }

    fun addData(data: List<Article?>) {
        items.addAll(data)
        notifyDataSetChanged()
    }
}

class ArticleViewHolder(
    private val binding: ItemArticleBinding,
    onArticleClicked: (Article) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var item: Article? = null

    init {
        binding.root.setOnClickListener { item?.let(onArticleClicked) }
    }

    fun setData(article: Article) = with(binding) {
        item = article
        image.loadImage(article.urlToImage)
        textTitle.text = article.title
        textSubTitle.text = article.description
        textTime.text = article.publishedAt
    }
}

class LoadMoreViewHolder(
    binding: ItemLoadMoreBinding
) : RecyclerView.ViewHolder(binding.root)
