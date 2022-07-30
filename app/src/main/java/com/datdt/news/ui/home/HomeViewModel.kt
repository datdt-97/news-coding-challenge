package com.datdt.news.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datdt.news.data.model.Article
import com.datdt.news.data.model.Result
import com.datdt.news.data.repository.NewsRepositoryType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NewsRepositoryType) : ViewModel() {

    private val _state = MutableLiveData<HomeUIState>()
    val state: LiveData<HomeUIState>
        get() = _state

    fun getArticles(query: String, page: Int) = viewModelScope.launch {
        if (page == 1) {
            _state.postValue(HomeUIState.Loading)
        }
        repository.getNews(query = query, page = page)
            .catch {
                _state.postValue(HomeUIState.Error(it))
            }
            .collectLatest {
                when (it) {
                    is Result.Error -> _state.postValue(HomeUIState.Error(it.error))
                    is Result.Success -> {
                        _state.postValue(HomeUIState.Success(it.value.articles))
                    }
                }
            }
    }
}

sealed interface HomeUIState {
    object Loading : HomeUIState
    data class Success(val articles: List<Article>) : HomeUIState
    data class Error(val error: Throwable) : HomeUIState
}
