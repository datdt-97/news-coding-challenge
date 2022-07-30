package com.datdt.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.datdt.news.data.model.Article
import com.datdt.news.databinding.FragmentHomeBinding
import com.datdt.news.extensions.invisible
import com.datdt.news.extensions.visible
import com.datdt.news.ui.custom.recyclerview.OnLoadMoreScrollListener
import com.datdt.news.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

    private val articleAdapter by lazy {
        ArticleAdapter(mutableListOf(), ::handleArticleClicked)
    }

    private lateinit var scrollListener: OnLoadMoreScrollListener

    private var currentPage = Constants.DEFAULT_PAGE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getArticles("Apple", currentPage)

        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            layoutNews.setOnRefreshListener(this@HomeFragment)

            recyclerNews.apply {
                setHasFixedSize(true)
                adapter = articleAdapter
                scrollListener = OnLoadMoreScrollListener(layoutManager!!) {
                    loadMore()
                }
                addOnScrollListener(scrollListener)
            }
        }

        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh() {
        currentPage = Constants.DEFAULT_PAGE
        viewModel.getArticles("Apple", currentPage)
    }

    private fun observeData() = with(binding) {
        viewModel.state.observe(viewLifecycleOwner) {
            layoutNews.isRefreshing = false
            articleAdapter.removeLoadingView()
            scrollListener.setLoaded()
            when (it) {
                is HomeUIState.Loading -> {
                    viewLoading.visible()
                    layoutError.invisible()
                    recyclerNews.invisible()
                }
                is HomeUIState.Error -> {
                    viewLoading.invisible()
                    layoutError.visible()
                    recyclerNews.invisible()

                    textErrorMessage.text = it.error.message
                }
                is HomeUIState.Success -> {
                    viewLoading.invisible()
                    layoutError.invisible()
                    recyclerNews.visible()

                    articleAdapter.addData(it.articles)
                }
            }
        }
    }

    private fun loadMore() {
        articleAdapter.addLoadingView()
        currentPage++
        viewModel.getArticles("Apple", currentPage)
    }

    private fun handleArticleClicked(article: Article) {
        Toast.makeText(context, article.title, Toast.LENGTH_SHORT).show()
    }
}
