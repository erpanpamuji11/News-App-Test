package com.methe.newsapptest.view.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.methe.newsapptest.data.remote.NewsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsEverythingViewModel @Inject constructor(private val newsApi: NewsApi): ViewModel() {

    fun getNews(query: String) = Pager(PagingConfig(pageSize = 1)) {
        NewsEverythingPagingSource(newsApi = newsApi, query)
    }.flow.cachedIn(viewModelScope)

}