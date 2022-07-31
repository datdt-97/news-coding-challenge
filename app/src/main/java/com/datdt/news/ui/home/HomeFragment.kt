package com.datdt.news.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.datdt.news.R
import com.datdt.news.data.model.Article
import com.datdt.news.databinding.FragmentHomeBinding
import com.datdt.news.extensions.invisible
import com.datdt.news.extensions.isNetworkAvailable
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isNetworkAvailable()) {
            viewModel.getArticles("Apple", currentPage)
        } else {
            viewModel.getCachedArticles()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        if (isNetworkAvailable()) {
            viewModel.getArticles("Apple", currentPage)
        } else {
            viewModel.getCachedArticles()
        }
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
                    recyclerNews.invisible()
                    if (currentPage == Constants.DEFAULT_PAGE) {
                        layoutError.visible()
                    } else {
                        Toast.makeText(
                            context,
                            "Cannot load more. Please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    textErrorMessage.text = it.error.message
                }
                is HomeUIState.Success -> {
                    viewLoading.invisible()
                    layoutError.invisible()
                    recyclerNews.visible()

                    articleAdapter.addData(it.articles)

                    if (isNetworkAvailable()) {
                        viewModel.saveCachedArticles(it.articles)
                    }
                }
                is HomeUIState.SaveCacheError -> {
                    Toast.makeText(
                        context,
                        it.error.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("TAG", "SaveCacheError: ${it.error.localizedMessage}")
                }
                HomeUIState.SaveCacheSuccess -> Unit
            }
        }
    }

    private fun loadMore() {
        if (isNetworkAvailable()) {
            articleAdapter.addLoadingView()
            currentPage++
            viewModel.getArticles("Apple", currentPage)
        }
    }

    private fun handleArticleClicked(article: Article) {
        val action = HomeFragmentDirections.actionHomeToDetail(article)
        findNavController().navigate(action)
    }
}
